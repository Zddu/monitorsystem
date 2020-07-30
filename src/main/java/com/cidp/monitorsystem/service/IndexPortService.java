package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.mapper.IndexPortMapper;
import com.cidp.monitorsystem.model.IndexPortRelate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexPortService {
    @Autowired
    IndexPortMapper indexPortMapper;

    public void addRelate(List<IndexPortRelate> relates) {
        indexPortMapper.addRelate(relates);
    }
}
