package org.neverplan2.wololo.net.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String ip;
    private String host;
    private String mac;
    private String type;
    private String comment;
}
