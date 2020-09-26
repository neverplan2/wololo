package org.neverplan2.wololo.service.net.ping;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.net.dto.Address;
import org.neverplan2.wololo.net.dto.Nic;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

}
