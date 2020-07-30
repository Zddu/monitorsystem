package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.MacForwardMapper;
import com.cidp.monitorsystem.model.MacForward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MacForwardService {
    @Autowired
    MacForwardMapper forwardMapper;

    public void addMac(List<MacForward> forwards) {
        forwardMapper.addMac(forwards);
    }
}
