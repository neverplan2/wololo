package org.neverplan2.wololo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.dao.NetAddressEntity;
import org.neverplan2.wololo.repo.NetAddressRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetAddressServiceTest {

    @InjectMocks
    protected NetAddressService netAddressService;

    @Mock
    private NetAddressRepository netAddressRepository;

    private static final String MAC_ADDRESS = "01:23:45:67:89:ab";

    @Test
    public void createNetAddress() {
        NetAddress newAddress = netAddressService.createNetAddress(MAC_ADDRESS, "Hello");
        assertEquals(MAC_ADDRESS, newAddress.getMac());
    }

    @Test
    public void addNetAddressList() {
        List<NetAddress> netAddresses = Arrays.asList(new NetAddress(MAC_ADDRESS), new NetAddress());
        List<NetAddressEntity> netAddressEntities = netAddresses.stream().map(netAddressService::modelToEntity).collect(toList());
        when(netAddressRepository.saveAll(netAddressEntities)).thenReturn(netAddressEntities);
        List<NetAddress> newAddress = netAddressService.addNetAddressList(netAddresses);
        assertEquals(2, newAddress.size());
        assertEquals(MAC_ADDRESS, newAddress.get(0).getMac());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNetAddress() {
        netAddressService.deleteNetAddress(new NetAddress(MAC_ADDRESS));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteNetAddressList() {
        netAddressService.deleteNetAddressList(singletonList(new NetAddress(MAC_ADDRESS)));
    }

    @Test
    public void getNetAddressList() {
        List<NetAddress> netAddresses = Arrays.asList(new NetAddress(MAC_ADDRESS), new NetAddress());
        List<NetAddressEntity> netAddressEntities = netAddresses.stream().map(netAddressService::modelToEntity).collect(toList());
        when(netAddressRepository.findAll()).thenReturn(netAddressEntities);
        List<NetAddress> newAddress = netAddressService.getNetAddressList();
        assertEquals(2, newAddress.size());
        assertEquals(MAC_ADDRESS, newAddress.get(0).getMac());
    }

    @Test
    public void updateNetAddress() {
        NetAddress newAddress = netAddressService.updateNetAddress(new NetAddress(MAC_ADDRESS));
        assertNull(newAddress);
    }

    @Test
    public void modelToEntity() {
        NetAddressEntity netAddressEntity = netAddressService.modelToEntity(null);
        assertNotNull(netAddressEntity);
    }

    @Test
    public void entityToModel() {
        NetAddress netAddress = netAddressService.entityToModel(null);
        assertNotNull(netAddress);
    }
}