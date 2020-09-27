package org.neverplan2.wololo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WolServiceTest {

    @InjectMocks
    protected WolService wolService;

    private static final String MAC_ADDRESS = "01:23:45:67:89:ab";

    @Test(expected = UnsupportedOperationException.class)
    public void wake() {
        wolService.wake(MAC_ADDRESS);
    }
}