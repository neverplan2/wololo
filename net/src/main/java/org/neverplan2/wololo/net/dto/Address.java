package org.neverplan2.wololo.net.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String ip;
    private String host;
    private String mac;
    private String type;
    private String comment;
}
