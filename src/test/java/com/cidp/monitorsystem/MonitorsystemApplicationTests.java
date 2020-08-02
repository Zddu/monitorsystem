package com.cidp.monitorsystem;
import com.cidp.monitorsystem.mapper.InterfaceOfMacMapper;
import com.cidp.monitorsystem.model.InterfaceOfMac;
import com.cidp.monitorsystem.topology.TopologyDiscovery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@EnableAsync
class MonitorsystemApplicationTests {
   @Autowired
    TopologyDiscovery topologyDiscovery;
   @Autowired
    InterfaceOfMacMapper macMapper;
    @Test
    void contextLoads() throws Exception {
//        topologyDiscovery.connectivelyOfL2ToL2();
        topologyDiscovery.connectOfAll();

    }

}
