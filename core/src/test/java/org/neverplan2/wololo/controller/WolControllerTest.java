package org.neverplan2.wololo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class WolControllerTest {

    @InjectMocks
    protected WolController controller;

    private MockMvc mvc;
    private final ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void wake() throws Exception {
        mvc.perform(post("/api/v1/wol/wake/?macAddress=01:23:45:67:89:ab"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }
}
