package org.neverplan2.wololo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleControllerTest {

    @InjectMocks
    private ScheduleController scheduleController;

    @Test
    public void test() {
        assertNotNull(scheduleController);
    }

}
