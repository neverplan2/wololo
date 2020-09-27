package org.neverplan2.wololo.api.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NetAddress {

    private String name;
    private String description;
    private String mac;
    private String address;
    private String hostname;
    private String cidr;
    private String broadcastAddress;
    private String comment;
    private boolean active;

    public NetAddress(String mac) {
        this.mac = mac;
    }
}
