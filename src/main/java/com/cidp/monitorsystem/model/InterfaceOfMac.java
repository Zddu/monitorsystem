package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class InterfaceOfMac {
    private String ip;
    private String ifindex;
    private String ifmac;
}
