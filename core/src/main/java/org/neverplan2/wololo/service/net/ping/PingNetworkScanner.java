package org.neverplan2.wololo.service.net.ping;

import lombok.extern.slf4j.Slf4j;
import org.neverplan2.wololo.net.NetworkScanner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PingNetworkScanner implements NetworkScanner {

    @Override
    public String getName() {
        return "Ping scanner";
    }

    @Override
    public List<Nic> getNetworkInterfaces() {
        List<Nic> nics = new ArrayList<>();
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            nics = nis.stream().filter(ni -> !PingUtil.isLocalAddress(ni)).map(PingUtil::mapNetworkInterface).collect(Collectors.toList());
        } catch (SocketException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return nics;
    }

    @Override
    public List<Address> scanNetwork(String nic) {
        return new ArrayList<>();
    }
}
