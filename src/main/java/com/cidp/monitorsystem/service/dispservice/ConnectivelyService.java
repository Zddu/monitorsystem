package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.ConnectivelyMapper;
import com.cidp.monitorsystem.model.Connectively;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectivelyService {
    @Autowired
    ConnectivelyMapper connectivelyMapper;

    public List<Connectively> getConnect() {
        return connectivelyMapper.getTrimConnect();
    }

    public List<Connectively> getConnectAll() {
        return connectivelyMapper.getConnect();
    }

    public boolean hasSip2Dip(String sip, String dip) {
        Connectively con = connectivelyMapper.hsaSip2Dip(sip,dip);
        if (con!=null){
            return true;
        }
        return false;
    }
    public boolean hasDip2Sip(String dip, String sip) {
        Connectively con = connectivelyMapper.hasDip2Sip(dip,sip);
        if (con!=null){
            return true;
        }
        return false;
    }

    public void addConnect(Connectively connectively) {
        connectivelyMapper.addConnect(connectively);
    }
}
