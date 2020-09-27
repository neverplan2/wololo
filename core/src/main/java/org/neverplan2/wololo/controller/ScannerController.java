package org.neverplan2.wololo.controller;

import lombok.AllArgsConstructor;
import org.neverplan2.wololo.api.endpoint.ScannerEndpoint;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.api.model.NicAdapter;
import org.neverplan2.wololo.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScannerController implements ScannerEndpoint {

    private final ScannerService scannerService;

    @Override
    public List<NicAdapter> scanNicAdapters() {
        return scannerService.scanNicAdapters();
    }

    @Override
    public List<NetAddress> scanNetwork(String address, String netmask) {
        return scannerService.scanNetwork(address, netmask);
    }
}
