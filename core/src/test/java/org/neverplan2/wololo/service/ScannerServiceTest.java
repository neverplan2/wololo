package org.neverplan2.wololo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.api.model.NicAdapter;
import org.neverplan2.wololo.dao.NetAddressEntity;
import org.neverplan2.wololo.dao.NicAdapterEntity;
import org.neverplan2.wololo.repo.NicAdapterRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScannerServiceTest {

    @InjectMocks
    protected ScannerService scannerService;

    @Mock
    private NicAdapterRepository nicAdapterRepository;


    @Test
    public void scanNicAdapters() {
        when(nicAdapterRepository.findAll()).thenReturn(Arrays.asList(new NicAdapterEntity(), new NicAdapterEntity()));
        List<NicAdapter> newAddress = scannerService.scanNicAdapters();
        assertEquals(2, newAddress.size());
    }

    @Test
    public void scanNetwork() {
        List<NetAddress> netAddresses = scannerService.scanNetwork("", "");
        assertNull(netAddresses);
    }

    @Test
    public void modelToEntity() {
        NicAdapter netAddressEntity = scannerService.entityToModel(null);
        assertNotNull(netAddressEntity);
    }
}