package org.neverplan2.wololo.net.ping;

import org.apache.commons.net.util.SubnetUtils;
import org.neverplan2.wololo.net.NetworkScanner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;
import org.neverplan2.wololo.net.exception.NetworkScannerException;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PingNetworkScanner implements NetworkScanner {

    private Logger log = Logger.getLogger(PingNetworkScanner.class.getName());

    private String byteToString(byte[] addr) {
        StringBuilder sb = new StringBuilder(18);
        for (byte b : addr) {
            if (sb.length() > 0)
                sb.append(':');
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private boolean isLocalAddress(NetworkInterface networkInterface) {
        try {
            return !networkInterface.isLoopback();
        } catch (SocketException e) {
            log.warning(e.getLocalizedMessage());
            return false;
        }
    }

    private void mapInterfaceAddress(InterfaceAddress interfaceAddress, Nic nic) {
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

    private Nic mapNetworkInterface(NetworkInterface networkInterface) {
        Nic nic = new Nic();
        nic.setDisplayName(networkInterface.getDisplayName());
        nic.setIndex(networkInterface.getIndex());
        nic.setName(networkInterface.getName());
        try {
            nic.setMac(byteToString(networkInterface.getHardwareAddress()));
            nic.setMtu(networkInterface.getMTU());
            nic.setState(networkInterface.isUp() ? Nic.NicState.UP : Nic.NicState.DOWN);
        } catch (SocketException e) {
            log.warning(e.getLocalizedMessage());
        }
        networkInterface.getInterfaceAddresses().forEach(a -> this.mapInterfaceAddress(a, nic));
        return nic;

    }

    @Override
    public String getName() {
        return "Ping scanner";
    }

    @Override
    public List<Nic> getNetworkInterfaces() throws NetworkScannerException {
        List<Nic> nics = new ArrayList<>();
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            nics = nis.stream().filter(this::isLocalAddress).map(this::mapNetworkInterface).collect(Collectors.toList());
        } catch (SocketException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return nics;
    }

    @Override
    public List<Address> scanNetwork(String nic) throws NetworkScannerException {
        return new ArrayList<Address>();
    }
}
