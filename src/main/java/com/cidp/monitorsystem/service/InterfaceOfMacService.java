package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.InterfaceOfMacMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InterfaceOfMacService {
    @Autowired
    InterfaceOfMacMapper macMapper;

    public void addMac(ArrayList<String> macs) {
        macMapper.addMac(macs);
    }
}
