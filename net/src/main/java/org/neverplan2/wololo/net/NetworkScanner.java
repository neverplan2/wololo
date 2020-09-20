package org.neverplan2.wololo.net;

import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;
import org.neverplan2.wololo.net.exception.NetworkScannerException;

import java.util.List;

public interface NetworkScanner {

    String getName();

    List<Nic> getNetworkInterfaces() throws NetworkScannerException;

    List<Address> scanNetwork(String nic) throws NetworkScannerException;

}
