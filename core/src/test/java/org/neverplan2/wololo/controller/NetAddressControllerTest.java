package org.neverplan2.wololo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.api.model.NetAddress;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class NetAddressControllerTest {

    @InjectMocks
    protected NetAddressController controller;

    private MockMvc mvc;
    private final ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/api/v1/address/create/?mac=01:23:45:67:89:ab&comment=Hello"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void add() throws Exception {
        mvc.perform(post("/api/v1/address/addlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{}]")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void update() throws Exception {
        NetAddress netAddress = new NetAddress();
        String s = om.writeValueAsString(netAddress);
        mvc.perform(post("/api/v1/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void deleteAddress() throws Exception {
        NetAddress netAddress = new NetAddress();
        String s = om.writeValueAsString(netAddress);
        mvc.perform(delete("/api/v1/address/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void getList() throws Exception {
        mvc.perform(get("/api/v1/address/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void deleteList() throws Exception {
        NetAddress netAddress = new NetAddress();
        String s = om.writeValueAsString(Collections.singletonList(netAddress));
        System.out.println(s);
        mvc.perform(delete("/api/v1/address/deletebatch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("")));
    }
}