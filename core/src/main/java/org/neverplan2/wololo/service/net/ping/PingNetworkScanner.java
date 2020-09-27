package org.neverplan2.wololo.service.net.ping;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.neverplan2.wololo.net.NetworkScanner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    public List<Address> scanNetwork(Nic nic) {
        SubnetUtils su = new SubnetUtils(nic.getIpv4Address(), nic.getNetmask());
        log.info("Start scanning network: " + su.getInfo().getCidrSignature());
        String[] allAddresses = su.getInfo().getAllAddresses();
        List<Address> addressList = new ArrayList<>();
        for (String addr: allAddresses) {
            try {
                if (pingHost(addr)) {
                    Address address = new Address();
                    InetAddress ia = InetAddress.getByName(addr);
                    address.setIp(ia.getHostAddress());
                    address.setHost(ia.getHostName());
                    address.setMac(PingUtil.getMacAaddress(addr));
                    addressList.add(address);
                }
            } catch (IOException | InterruptedException e) {
                log.warn(e.getLocalizedMessage(), e);
            }
        }
        return addressList;
    }

    public boolean pingHost(String host) throws IOException, InterruptedException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder processBuilder = new ProcessBuilder("ping", isWindows ? "-n" : "-c", "1", host);
        Process proc = processBuilder.start();
        StreamLogger outLogger = new StreamLogger(proc.getInputStream(), StreamLogger.StreamType.OUT);
        StreamLogger errLogger = new StreamLogger(proc.getErrorStream(), StreamLogger.StreamType.ERROR);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(outLogger);
        executorService.submit(errLogger);

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        int returnVal = proc.waitFor();
        return returnVal == 0;
    }
}
