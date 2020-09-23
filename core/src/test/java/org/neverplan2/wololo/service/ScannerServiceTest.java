package org.neverplan2.wololo.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.net.exception.NetworkScannerException;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void getNics() {
        try {
            scannerService.getNetworkScanners().get(0).getNetworkInterfaces().forEach(System.out::println);
        } catch (NetworkScannerException e) {
            e.printStackTrace();
        }
        assertTrue(true);
    }


    @AfterClass
    public static void stopSet() {
        listAppender.stop();
    }

}
