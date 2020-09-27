package org.neverplan2.wololo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "net_address")
public class NetAddressEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private String mac;
    private String address;
    private String hostname;
    private String cidr;
    @Column(name = "broadcast_address")
    private String broadcastAddress;
    private String comment;
    private boolean active;
}
