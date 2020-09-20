package org.neverplan2.wololo.service;

import lombok.extern.slf4j.Slf4j;
import org.neverplan2.wololo.net.NetworkScanner;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScannerService {

    private ServiceLoader<NetworkScanner> serviceLoader;

    @PostConstruct
    public void init(){
        serviceLoader = ServiceLoader.load(NetworkScanner.class);
        if (!serviceLoader.iterator().hasNext()) {
            log.warn("No scanner registered.");
        }
    }

    public List<NetworkScanner> getNetworkScanners() {
        return serviceLoader.stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

}
