package org.neverplan2.wololo.controller;

import org.neverplan2.wololo.api.endpoint.ScannerEndpoint;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.api.model.NicAdapter;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScannerController implements ScannerEndpoint {
    @Override
    public List<NicAdapter> scanNicAdapters() {
        return null;
    }

    @Override
    public List<NetAddress> scanNetwork(String address, String netmask) {
        return null;
    }
}
