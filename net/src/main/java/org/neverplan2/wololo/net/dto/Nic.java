package org.neverplan2.wololo.net.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Nic {

    public enum NicState{
        UP,
        DOWN
    }

    private String device;
    private String name;
    private String displayName;
    private String host;
    private String mac;
    private String broadcast;
    private String netmask;
    private String ipv4Address;
    private String ipv6Address;
    private int index;
    private int mtu;
    private NicState state;
}
