package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.DeviceViewMapper;
import com.cidp.monitorsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @date 2020/5/8 -- 7:47
 **/
@Service
public class DeviceViewService {
    @Autowired
    private DeviceViewMapper deviceViewMapper;
    public List<Cpu> getCupInfoByIp(String ip) {
        return deviceViewMapper.selectCupByIp(ip);
    }

    public List<Memory> getMemoryInfoByIp(String ip) {
        return deviceViewMapper.selectMemoryByIp(ip);
    }

    public List<Interface> getInfaceInfo(String ip) {
        return deviceViewMapper.selectInterfaceByIp(ip);
    }

    public List<InterFlow> getInfaceflow(String ip, String interDescr) {
        return deviceViewMapper.selectInterfaceflowByInter(ip,interDescr);
    }

    public List<SystemInfo> getDevices() {
        return deviceViewMapper.getDevice();
    }

    public List<InterFlow> getAllInfaceflow(String ip) {
        return deviceViewMapper.selecAllInfaceFlow(ip);
    }

    public List<Interface> getInfaceInfoFilterVlan(String ip) {
        return deviceViewMapper.selectInterfaceFilterVlanByIp(ip);
    }

    public List<Interface> getInfaceInfoFilterEmptydata(String ip) {
        return deviceViewMapper.selectInfaceInfoFilterEmptydata(ip);
    }
}
