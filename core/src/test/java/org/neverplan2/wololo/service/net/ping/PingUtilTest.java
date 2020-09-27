package org.neverplan2.wololo.service.net.ping;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.net.dto.Nic;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PingUtilTest {

    @InjectMocks
    private PingNetworkScanner pns;

    @Test
    public void byteToStringTest() {
        assertEquals("11:22:33:44:55:66", PingUtil.byteToString(new byte[]{17,34,51,68,85,102}));
    }

    @Test
    public void isLocalAddressTest() throws SocketException {
        NetworkInterface ni = NetworkInterface.getNetworkInterfaces().nextElement();
        assertEquals(ni.isLoopback(), PingUtil.isLocalAddress(ni));
    }

    @Test
    public void isLocalAddressExceptionTest() throws SocketException {
        NetworkInterface ni = mock(NetworkInterface.class);
        when(ni.isLoopback()).thenThrow(new SocketException());
        assertTrue(PingUtil.isLocalAddress(ni));
    }

    @Test
    public void mapInterfaceAddress4Test() {
        InetAddress iab = mock(InetAddress.class);
        when(iab.getHostAddress()).thenReturn("10.0.0.255");

        Inet4Address i4a = mock(Inet4Address.class);
        when(i4a.getCanonicalHostName()).thenReturn("test");
        when(i4a.getHostAddress()).thenReturn("10.0.0.1");

        InterfaceAddress ia = mock(InterfaceAddress.class);
        when(ia.getNetworkPrefixLength()).thenReturn((short) 24);
        when(ia.getAddress()).thenReturn(i4a);
        when(ia.getBroadcast()).thenReturn(iab);

        Nic nic = new Nic();
        PingUtil.mapInterfaceAddress(ia, nic);

        assertEquals(i4a.getCanonicalHostName(), nic.getHost());
        assertEquals(i4a.getHostAddress(), nic.getIpv4Address());
        assertEquals("255.255.255.0", nic.getNetmask());
        assertEquals("10.0.0.255", nic.getBroadcast());
    }

    @Test
    public void mapInterfaceAddress6Test() {
        Inet6Address i6a = mock(Inet6Address.class);
        when(i6a.getHostAddress()).thenReturn("1111:2222:3333:4444:5555:6666:7777:8888");

        InterfaceAddress ia = mock(InterfaceAddress.class);
        when(ia.getAddress()).thenReturn(i6a);

        Nic nic = new Nic();
        PingUtil.mapInterfaceAddress(ia, nic);

        assertEquals(i6a.getHostAddress(), nic.getIpv6Address());
    }

    @Test
    public void mapNetworkInterface() throws SocketException {
        NetworkInterface ni = mock(NetworkInterface.class);
        when(ni.getDisplayName()).thenReturn("test");
        when(ni.getIndex()).thenReturn(1);
        when(ni.getName()).thenReturn("name");
        when(ni.getHardwareAddress()).thenReturn(new byte[]{17,34,51,68,85,102});
        when(ni.getMTU()).thenReturn(1000);
        when(ni.isUp()).thenReturn(true);

        InterfaceAddress if4a = mock(InterfaceAddress.class);
        Inet4Address i4a = mock(Inet4Address.class);
        when(i4a.getHostAddress()).thenReturn("10.0.0.1");
        when(if4a.getNetworkPrefixLength()).thenReturn((short) 24);
        when(if4a.getAddress()).thenReturn(i4a);
        InterfaceAddress if6a = mock(InterfaceAddress.class);
        Inet6Address i6a = mock(Inet6Address.class);
        when(i6a.getHostAddress()).thenReturn("1111:2222:3333:4444:5555:6666:7777:8888");
        when(if6a.getAddress()).thenReturn(i6a);

        when(ni.getInterfaceAddresses()).thenReturn(new ArrayList<InterfaceAddress>(){{add(if4a);add(if6a);}});

        Nic nic = PingUtil.mapNetworkInterface(ni);

        assertEquals(ni.getDisplayName(), nic.getDisplayName());
        assertEquals(ni.getIndex(), nic.getIndex());
        assertEquals(ni.getName(), nic.getName());
        assertEquals("11:22:33:44:55:66", nic.getMac());
        assertEquals(ni.getMTU(), nic.getMtu());
        assertEquals(nic.getState(), Nic.NicState.UP);
        assertNotNull(nic.getIpv4Address());
        assertNotNull(nic.getIpv6Address());
    }

    @Ignore
    @Test
    public void getMacAaddressTest() throws IOException, InterruptedException {
        Nic nic = pns.getNetworkInterfaces().get(0);
        assertNull(PingUtil.getMacAaddress(nic.getIpv4Address()));
    }

}