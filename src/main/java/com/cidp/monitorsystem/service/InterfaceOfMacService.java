package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.InterfaceOfMacMapper;
import com.cidp.monitorsystem.model.InterfaceOfMac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterfaceOfMacService {
    @Autowired
    InterfaceOfMacMapper macMapper;

    public void addMac(List<InterfaceOfMac> macs) {
        macMapper.addMac(macs);
    }
}
