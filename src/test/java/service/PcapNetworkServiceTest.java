package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.exception.WololoException;
import org.neverplan2.wololo.service.impl.pcap.ArpPacketUtil;
import org.neverplan2.wololo.service.impl.pcap.PcapNetworkService;

import static org.junit.Assert.assertTrue;

//@RunWith(MockitoJUnitRunner.class)
public class PcapNetworkServiceTest {

    @Mock
    ArpPacketUtil arppu;
    @InjectMocks
    PcapNetworkService ns;

    //@Test
    public void getNetworkInterfaces() throws WololoException {
        ns.getNetworkInterfaces().stream().forEach(n -> System.out.println(n.getName()));
        assertTrue(true);
    }

    //@Test
    public void scanNetwork() throws WololoException {
        ns.scanNetwork("enp0s3").stream().forEach(s -> System.out.println(s.getIp()));;
        assertTrue(true);
    }

}
