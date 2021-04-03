package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Connectively;
import com.cidp.monitorsystem.model.SystemInfo;
import com.cidp.monitorsystem.service.CpuService;
import com.cidp.monitorsystem.service.MemoryService;
import com.cidp.monitorsystem.service.SystemService;
import com.cidp.monitorsystem.service.dispservice.ConnectivelyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/topo")
@RestController
public class TopologyController {
    @Autowired
    ConnectivelyService connectivelyService;
    @Autowired
    SystemService systemService;
    @Autowired
    CpuService cpuService;
    @Autowired
    MemoryService memoryService;
    @GetMapping("connect")
    public List<Connectively> connectivelies(){
        return connectivelyService.getConnectAll();
    }
    @GetMapping("node")
    public List<SystemInfo> getNodes(){
        return systemService.getAllActNode();
    }

}


