package org.neverplan2.wololo.net.ping;

import org.neverplan2.wololo.net.NetworkScanner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;
import org.neverplan2.wololo.net.exception.NetworkScannerException;

import java.util.List;

public class PingNetworkScanner implements NetworkScanner {


    @Override
    public String getName() {
        return "Ping scanner";
    }

    @Override
    public List<Nic> getNetworkInterfaces() throws NetworkScannerException {
        return null;
    }

    @Override
    public List<Address> scanNetwork(String nic) throws NetworkScannerException {
        return null;
    }
}
