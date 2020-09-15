package org.neverplan2.wololo.service.impl.pcap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.neverplan2.wololo.exception.WololoException;
import org.neverplan2.wololo.model.Address;
import org.neverplan2.wololo.model.Nic;
import org.neverplan2.wololo.service.NetworkService;
import org.pcap4j.core.*;
import org.pcap4j.util.MacAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PcapNetworkService implements NetworkService {

    @Autowired
    private ArpPacketUtil arpPacketUtil;

    @Override
    public List<Nic> getNetworkInterfaces() throws WololoException {
        List<Nic> nics = new ArrayList<Nic>();

        List<PcapNetworkInterface> pniList = null;
        try {
            pniList = Pcaps.findAllDevs();
        } catch (PcapNativeException e) {
            throw new WololoException("Failed to retrieve network interfaces.", e);
        }
        for (PcapNetworkInterface pcapNetworkInterface : pniList) {
            Nic nic = new Nic();
            nic.setDevice(pcapNetworkInterface.getName());
            nic.setDisplayName(pcapNetworkInterface.getDescription());

            List<PcapAddress> addrList = pcapNetworkInterface.getAddresses();
            for(PcapAddress pcapAddr : addrList) {
                if (pcapAddr instanceof PcapIpV4Address) {
                    NetworkInterface ni = null;
                    try {
                        ni = NetworkInterface.getByInetAddress(pcapAddr.getAddress());
                        nic.setName(ni.getName());
                        nic.setIndex(ni.getIndex());
                        nic.setMtu(ni.getMTU());
                        nic.setMac(ni.getHardwareAddress() ==  null ? "" : MacAddress.getByAddress(ni.getHardwareAddress()).toString());
                        nic.setState(ni.isUp() ? Nic.NicState.UP : Nic.NicState.DOWN);
                        nic.setHost(pcapAddr.getAddress().getHostName());
                        nic.setIpv4Address(pcapAddr.getAddress().getHostAddress());
                    } catch (SocketException e) {
                        log.error("Failed to get details for address: " + pcapAddr.getAddress(), e);
                    }
                } else {
                    nic.setIpv6Address(pcapAddr.getAddress().getHostAddress());
                }
                if (pcapAddr.getBroadcastAddress() != null) {
                    nic.setBroadcast(pcapAddr.getBroadcastAddress().getHostAddress());
                }
                if (pcapAddr.getNetmask() != null) {
                    nic.setNetmask(pcapAddr.getNetmask().getHostAddress());
                }
            }
            nics.add(nic);
        }

        return nics;
    }

    @Override
    public List<Address> scanNetwork(String nic) throws WololoException{
        PcapNetworkInterface pni = null;
        try {
            pni = Pcaps.getDevByName(nic);
        } catch (PcapNativeException e) {
            throw new WololoException("Failed to scan network.", e);
        }
        PcapAddress pcapAddr = pni.getAddresses().stream().filter(a->a instanceof PcapIpV4Address).findFirst().get();
        SubnetUtils su = new SubnetUtils(pcapAddr.getAddress().getHostAddress(), pcapAddr.getNetmask().getHostAddress());
        return arpPacketUtil.getMacAddress(pni, Arrays.asList(su.getInfo().getAllAddresses()))
                .stream().map(a->new Address(a.getSrcProtocolAddr().getHostAddress(), a.getSrcProtocolAddr().getCanonicalHostName(), a.getSrcHardwareAddr().toString(), "", "")).collect(Collectors.toList());
    }
}
