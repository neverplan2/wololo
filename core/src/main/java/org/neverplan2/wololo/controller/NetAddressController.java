package org.neverplan2.wololo.controller;

import lombok.AllArgsConstructor;
import org.neverplan2.wololo.api.endpoint.NetAddressEndpoint;
import org.neverplan2.wololo.api.model.NetAddress;
import org.neverplan2.wololo.service.NetAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NetAddressController implements NetAddressEndpoint {

    private final NetAddressService netAddressService;

    @Override
    public NetAddress createNetAddress(String mac, String comment) {
        return netAddressService.createNetAddress(mac, comment);
    }

    @Override
    public List<NetAddress> addNetAddressList(List<NetAddress> netAddressList) {
        return netAddressService.addNetAddressList(netAddressList);
    }

    @Override
    public NetAddress updateNetAddress(NetAddress netAddress) {
        return netAddressService.updateNetAddress(netAddress);
    }

    @Override
    public void deleteNetAddress(NetAddress netAddress) {
        netAddressService.deleteNetAddress(netAddress);
    }

    @Override
    public void deleteNetAddressList(List<NetAddress> netAddressList) {
        netAddressService.deleteNetAddressList(netAddressList);
    }

    @Override
    public List<NetAddress> getNetAddressList() {
        return netAddressService.getNetAddressList();
    }

}
