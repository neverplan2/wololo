package org.neverplan2.wololo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ScannerServiceTest {

    @InjectMocks
    protected ScannerService scannerService;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        this.scannerService.init(); //your Injected bean
    }

    @Test
    public void initTest() {
        assertNotNull(scannerService);
    }

    @Test
    public void scanners() {
        assertEquals(scannerService.getNetworkScanners().size(), 1);
    }

}
