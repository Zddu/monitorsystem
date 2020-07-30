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
//        topologyDiscovery.deviceDiscoveryByMac();
        List<InterfaceOfMac> list = new ArrayList<>();
        InterfaceOfMac mac = new InterfaceOfMac();
        mac.setIp("1111");
        mac.setIfmac("1112");
        mac.setIfindex("1");
        list.add(mac);
        macMapper.addMac(list);
    }

}
