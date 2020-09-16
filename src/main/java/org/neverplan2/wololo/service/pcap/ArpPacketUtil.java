package org.neverplan2.wololo.service.pcap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.ArpPacket.ArpHeader;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ArpPacketUtil {

    private static final int SNAPLEN = 65536;
    private int packetCount = 3;
    private int packetDelay = 1000;
    private int timeout = 2000;

    @Getter
    private class ArpPacketListener implements PacketListener {

        private ArpHeader arpHeader = null;

        @Override
        public void gotPacket(Packet packet) {
            if (packet.contains(ArpPacket.class)) {
                ArpPacket arp = packet.get(ArpPacket.class);
                if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                    this.arpHeader = arp.getHeader();
                }
            }
            log.info(packet.toString());
        }
    };

    public List<ArpHeader> getMacAddress(PcapNetworkInterface pni, List<String> ipList) {
        ExecutorService es = Executors.newFixedThreadPool(100);

        List<Future<ArpHeader>> futures = new ArrayList<>();

        for (String address : ipList) {
            futures.add(es.submit(() -> {
                try {
                    InetAddress inetAddr = InetAddress.getByName(address);
                    return getMacAddress(pni, inetAddr);
                } catch (PcapNativeException | NotOpenException | SocketException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
                return null;
            }));
        }

        List<ArpHeader> result = futures.stream().map(f -> {
            try {
                return f.get();
            } catch (ExecutionException e) {
                log.error(e.getLocalizedMessage(), e);
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
                Thread.currentThread().interrupt();
            }
            return null;
        }).filter(s -> s != null).collect(Collectors.toList());

        es.shutdown();

        return result;
    }

    public ArpHeader getMacAddress(PcapNetworkInterface pni, InetAddress destAddr) throws SocketException, PcapNativeException, NotOpenException, InterruptedException {
        PcapAddress addr = pni.getAddresses().stream().filter(a -> (a instanceof PcapIpV4Address)).findFirst().orElse(null);
        if (addr == null) {
            return null;
        }
        NetworkInterface ni = NetworkInterface.getByInetAddress(addr.getAddress());
        MacAddress srcMacAddr = MacAddress.getByAddress(ni.getHardwareAddress());
        InetAddress srcAddr = addr.getAddress();

        ExecutorService pool = Executors.newSingleThreadExecutor();

        ArpHeader arpHeader = null;

        try(PcapHandle handle = pni.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, timeout);
            PcapHandle sendHandle = pni.openLive(SNAPLEN, PromiscuousMode.PROMISCUOUS, timeout)) {
            handle.setFilter("arp and src host " + destAddr.getHostAddress() + " and dst host " + srcAddr.getHostAddress() + " and ether dst " + Pcaps.toBpfString(srcMacAddr), BpfCompileMode.OPTIMIZE);

            Future<ArpHeader> future = pool.submit(() -> {
                ArpPacketListener listener = new ArpPacketListener();
                try {
                    handle.loop(packetCount, listener);
                } catch (PcapNativeException | NotOpenException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
                return listener.arpHeader;
            });

            ArpPacket.Builder arpBuilder = getArpBuilder(srcMacAddr, srcAddr, destAddr);
            EthernetPacket.Builder etherBuilder = getEtherBuilder(srcMacAddr, arpBuilder);

            for (int i = 0; i < packetCount; i++) {
                Packet p = etherBuilder.build();
                log.info(p.toString());
                sendHandle.sendPacket(p);
                Thread.sleep(packetDelay);
            }

            try {
                if (!pool.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                    handle.breakLoop();
                }
                arpHeader = future.get();
            } catch (ExecutionException e) {
                log.error(e.getLocalizedMessage(), e);
            }

        } finally {
            if (pool != null && !pool.isShutdown()) {
                pool.shutdown();
            }

        }
        return arpHeader;
    }

    private ArpPacket.Builder getArpBuilder(MacAddress macAddr, InetAddress srcAddr, InetAddress destAddr) {
        return new ArpPacket.Builder().hardwareType(ArpHardwareType.ETHERNET).protocolType(EtherType.IPV4).hardwareAddrLength((byte) MacAddress.SIZE_IN_BYTES).protocolAddrLength((byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES).operation(ArpOperation.REQUEST).srcHardwareAddr(macAddr).srcProtocolAddr(srcAddr).dstHardwareAddr(MacAddress.ETHER_BROADCAST_ADDRESS).dstProtocolAddr(destAddr);
    }

    private EthernetPacket.Builder getEtherBuilder(MacAddress macAddr, ArpPacket.Builder arpBuilder) {
        return new EthernetPacket.Builder().dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS).srcAddr(macAddr).type(EtherType.ARP).payloadBuilder(arpBuilder).paddingAtBuild(true);
    }

}
