package org.neverplan2.wololo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nic_adapter")
public class NicAdapterEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    private String name;
    @Column(name = "display_name")
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
