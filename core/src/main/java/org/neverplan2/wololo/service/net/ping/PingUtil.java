package org.neverplan2.wololo.service.net.ping;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.neverplan2.wololo.net.dto.Nic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
class PingUtil {

    String byteToString(byte[] addr) {
        StringBuilder sb = new StringBuilder(18);
        for (byte b : addr) {
            if (sb.length() > 0)
                sb.append(':');
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean isLocalAddress(NetworkInterface networkInterface) {
        try {
            return networkInterface.isLoopback();
        } catch (SocketException e) {
            log.error(e.getLocalizedMessage());
            return true;
        }
    }

    void mapInterfaceAddress(InterfaceAddress interfaceAddress, Nic nic) {
        if (interfaceAddress.getAddress() instanceof Inet4Address) {
            Inet4Address addr = (Inet4Address) interfaceAddress.getAddress();
            nic.setHost(addr.getCanonicalHostName());
            nic.setIpv4Address(addr.getHostAddress());
            SubnetUtils su = new SubnetUtils(nic.getIpv4Address() + "/" + interfaceAddress.getNetworkPrefixLength());
            nic.setNetmask(su.getInfo().getNetmask());
            if (interfaceAddress.getBroadcast() != null) {
                nic.setBroadcast(interfaceAddress.getBroadcast().getHostAddress());
            }
        } else {
            Inet6Address addr = (Inet6Address) interfaceAddress.getAddress();
            nic.setIpv6Address(addr.getHostAddress());
        }
    }

    Nic mapNetworkInterface(NetworkInterface networkInterface) {
        Nic nic = new Nic();
        nic.setDisplayName(networkInterface.getDisplayName());
        nic.setIndex(networkInterface.getIndex());
        nic.setName(networkInterface.getName());
        try {
            nic.setMac(byteToString(networkInterface.getHardwareAddress()));
            nic.setMtu(networkInterface.getMTU());
            nic.setState(networkInterface.isUp() ? Nic.NicState.UP : Nic.NicState.DOWN);
        } catch (SocketException e) {
            log.error(e.getLocalizedMessage());
        }
        networkInterface.getInterfaceAddresses().forEach(a -> PingUtil.mapInterfaceAddress(a, nic));
        return nic;

    }

    public String getMacAaddress(String param) throws IOException {
        Process p = Runtime.getRuntime().exec("arp -a " + param);
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = input.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                log.info(line);
                // keep only the process name
                line = line.substring(1);
                Pattern pattern = Pattern.compile("..:..:..:..:..:..");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return matcher.group();
                }
            }

        }
        return null;
    }

}
