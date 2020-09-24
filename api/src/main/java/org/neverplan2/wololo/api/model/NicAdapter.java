package org.neverplan2.wololo.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NicAdapter {

    private String name;
    private String displayName;
    private String hostname;
    private String mac;
    private String broadcast;
    private String netmask;
    private String ipv4Address;
    private String ipv6Address;
    private int index;
    private int mtu;
}
