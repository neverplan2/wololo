package org.neverplan2.wololo.net.ping;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.powermock.api.mockito.PowerMockito.doReturn;

@Ignore
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest
public class PingNetworkScannerTest {

    @InjectMocks
    private PingNetworkScanner pingNetworkScanner;

    @Test
    public void byteToStringTest() throws Exception {
        doReturn("").when(pingNetworkScanner,"byteToString", new byte[]{1});
    }
}
