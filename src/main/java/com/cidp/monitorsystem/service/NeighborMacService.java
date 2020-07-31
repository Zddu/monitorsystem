package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.NeighborMacMapper;
import com.cidp.monitorsystem.model.NeighborMac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeighborMacService {
    @Autowired
    NeighborMacMapper neighborMacMapper;

    public void addRemMac(List<NeighborMac> neighborMacs) {
        neighborMacMapper.addRemMac(neighborMacs);
    }

    public boolean getRemMac(String ip) {
        neighborMacMapper.getRemMac(ip);
        return false;
    }
}
