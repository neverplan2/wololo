package org.neverplan2.wololo.service;

import lombok.AllArgsConstructor;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.api.model.NicAdapter;
import org.neverplan2.wololo.dao.NicAdapterEntity;
import org.neverplan2.wololo.repo.NicAdapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScannerService {

    private final NicAdapterRepository nicAdapterRepository;

    public List<NicAdapter> scanNicAdapters() {
        List<NicAdapterEntity> collect = StreamSupport.stream(nicAdapterRepository.findAll().spliterator(), false).collect(toList());
        return collect.stream().map(this::entityToModel).collect(toList());
    }

    public List<NetAddress> scanNetwork(String address, String netmask) {
        return null;
    }

    public NicAdapter entityToModel(NicAdapterEntity nicAdapterEntity) {
        if (null == nicAdapterEntity) return new NicAdapter();
        NicAdapter netAddress = new NicAdapter();
        netAddress.setName(nicAdapterEntity.getName());
        netAddress.setDisplayName(nicAdapterEntity.getDisplayName());
        netAddress.setHostname(nicAdapterEntity.getHostname());
        netAddress.setMac(nicAdapterEntity.getMac());
        netAddress.setBroadcast(nicAdapterEntity.getBroadcast());
        netAddress.setNetmask(nicAdapterEntity.getNetmask());
        netAddress.setIpv4Address(nicAdapterEntity.getIpv4Address());
        netAddress.setIpv6Address(nicAdapterEntity.getIpv6Address());
        netAddress.setIndex(nicAdapterEntity.getIndex());
        netAddress.setMtu(nicAdapterEntity.getMtu());
        return netAddress;
    }


}
