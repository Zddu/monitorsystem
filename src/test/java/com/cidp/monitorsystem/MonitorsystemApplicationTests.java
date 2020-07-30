package com.cidp.monitorsystem;
import com.cidp.monitorsystem.topology.TopologyDiscovery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootTest
@EnableAsync
class MonitorsystemApplicationTests {
   @Autowired
    TopologyDiscovery topologyDiscovery;
    @Test
    void contextLoads() throws Exception {
        topologyDiscovery.deviceDiscoveryByMac();
    }

}
