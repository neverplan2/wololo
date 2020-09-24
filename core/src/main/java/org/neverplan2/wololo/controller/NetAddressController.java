package org.neverplan2.wololo.controller;

import org.neverplan2.wololo.api.endpoint.NetAddressEndpoint;
import org.neverplan2.wololo.api.model.NetAddress;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NetAddressController implements NetAddressEndpoint {

    @Override
    public NetAddress createNetAddress(String mac, String comment) {
        return null;
    }

    @Override
    public NetAddress addNetAddressList(List<NetAddress> netAddressList) {
        return null;
    }

    @Override
    public NetAddress updateNetAddress(NetAddress netAddress) {
        return null;
    }

    @Override
    public void deleteNetAddress(NetAddress netAddress) {

    }

    @Override
    public List<NetAddress> getNetAddressList() {
        return null;
    }

    @Override
    public void deleteNetAddressList(List<NetAddress> netAddressList) {

    }
}
