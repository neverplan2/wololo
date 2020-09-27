package org.neverplan2.wololo.service;

import lombok.AllArgsConstructor;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.dao.NetAddressEntity;
import org.neverplan2.wololo.repo.NetAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NetAddressService {

    private final NetAddressRepository netAddressRepository;

    public NetAddress createNetAddress(String mac, String comment) {
        NetAddress netAddress = new NetAddress();
        netAddress.setMac(mac);
        netAddress.setComment(comment);
        netAddressRepository.save(modelToEntity(netAddress));
        return netAddress;
    }

    public List<NetAddress> addNetAddressList(List<NetAddress> netAddressList) {
        List<NetAddressEntity> netAddressEntities = netAddressList.stream().map(this::modelToEntity).collect(toList());
        Iterable<NetAddressEntity> persistedEntities = netAddressRepository.saveAll(netAddressEntities);
        List<NetAddressEntity> collect = StreamSupport.stream(persistedEntities.spliterator(), false).collect(toList());
        return collect.stream().map(this::entityToModel).collect(toList());
    }

    public void deleteNetAddress(NetAddress netAddress) {
        throw new UnsupportedOperationException();
    }

    public void deleteNetAddressList(List<NetAddress> netAddressList) {
        throw new UnsupportedOperationException();
    }

    public List<NetAddress> getNetAddressList() {
        List<NetAddressEntity> collect = StreamSupport.stream(netAddressRepository.findAll().spliterator(), false).collect(toList());
        return collect.stream().map(this::entityToModel).collect(toList());
    }

    public NetAddress updateNetAddress(NetAddress netAddress) {
        return null;
    }

    public NetAddressEntity modelToEntity(NetAddress netAddress) {
        if (null == netAddress) return new NetAddressEntity();
        NetAddressEntity netAddressEntity = new NetAddressEntity();
        netAddressEntity.setName(netAddress.getName());
        netAddressEntity.setDescription(netAddress.getDescription());
        netAddressEntity.setMac(netAddress.getMac());
        netAddressEntity.setAddress(netAddress.getAddress());
        netAddressEntity.setHostname(netAddress.getHostname());
        netAddressEntity.setCidr(netAddress.getCidr());
        netAddressEntity.setBroadcastAddress(netAddress.getBroadcastAddress());
        netAddressEntity.setComment(netAddress.getComment());
        netAddressEntity.setActive(netAddress.isActive());
        return netAddressEntity;
    }

    public NetAddress entityToModel(NetAddressEntity netAddressEntity) {
        if (null == netAddressEntity) return new NetAddress();
        NetAddress netAddress = new NetAddress();
        netAddress.setName(netAddressEntity.getName());
        netAddress.setDescription(netAddressEntity.getDescription());
        netAddress.setMac(netAddressEntity.getMac());
        netAddress.setAddress(netAddressEntity.getAddress());
        netAddress.setHostname(netAddressEntity.getHostname());
        netAddress.setCidr(netAddressEntity.getCidr());
        netAddress.setBroadcastAddress(netAddressEntity.getBroadcastAddress());
        netAddress.setComment(netAddressEntity.getComment());
        netAddress.setActive(netAddressEntity.isActive());
        return netAddress;
    }

}
