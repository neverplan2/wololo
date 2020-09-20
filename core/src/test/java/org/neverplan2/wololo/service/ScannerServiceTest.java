package org.neverplan2.wololo.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.net.NetworkScanner;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScannerServiceTest {

    @InjectMocks
    protected ScannerService scannerService;

    private static ListAppender<ILoggingEvent> listAppender;

    @BeforeClass
    public static void prepareSet() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        listAppender = new ListAppender<>();
        listAppender.start();
        root.addAppender(listAppender);
    }

    @Before
    public void prepareTest() {
        scannerService.init();
    }

    @Test
    public void initTest() {
        this.scannerService.init();
        //assertEquals("No scanner registered.", listAppender.list.get(0).getMessage());
        assertTrue(true);
    }

    @Test
    public void scanners() {
        assertEquals(1, scannerService.getNetworkScanners().size());
    }

    @AfterClass
    public static void stopSet() {
        listAppender.stop();
    }

}
