package org.neverplan2.wololo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Nic {

    public enum NicState{
        UP,
        DOWN
    };

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
