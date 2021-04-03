package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;
@Data
public class TopoData {
    private List<SystemInfo> systemInfo;
    private List<Memory> memory;
    private List<Cpu> cpu;
}
