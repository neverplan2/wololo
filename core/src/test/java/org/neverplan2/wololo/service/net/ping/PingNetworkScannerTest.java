package org.neverplan2.wololo.service.net.ping;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PingNetworkScannerTest {

    @InjectMocks
    private PingNetworkScanner pingNetworkScanner;

    @Test
    public void getNameTest() throws Exception {
        assertEquals("Ping scanner", pingNetworkScanner.getName());
    }

    @Test
    public void getNetworkInterfacesTest() throws Exception {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        List<Nic> nics = pingNetworkScanner.getNetworkInterfaces();
        assertEquals(Collections.list(nis).size() - 1, nics.size());
    }

    @Test
    public void pingTest() throws IOException, InterruptedException {
        assertTrue(pingNetworkScanner.pingHost("192.168.1.1"));
    }

    @Ignore
    @Test
    public void scanNetworkTest() throws IOException, InterruptedException {
        List<Address> addressList = pingNetworkScanner.scanNetwork(pingNetworkScanner.getNetworkInterfaces().get(0));
        addressList.forEach(System.out::println);
        assertNotEquals(0, addressList.size());
    }

}
