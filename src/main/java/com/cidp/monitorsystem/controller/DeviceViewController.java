package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.*;
import com.cidp.monitorsystem.service.dispservice.DeviceViewService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


/**
 * @date 2020/5/8 -- 7:43
 **/
@RestController
@RequestMapping("/device")
public class DeviceViewController {
    @Autowired
    private DeviceViewService deviceViewService;
    @GetMapping("/select")
    public List<SystemInfo> getDevices(){
        return deviceViewService.getDevices();
    }
    //cpu使用率
    @RequestMapping("/getcup")
    public List<Cpu> showCpu(@RequestParam String ip){
        return deviceViewService.getCupInfoByIp(ip);
    }
    //内存使用率
    @RequestMapping("/getmemory")
    public List<Memory> showMemory(@RequestParam String ip){
        return deviceViewService.getMemoryInfoByIp(ip);
    }
    //返回服务器接口信息
    @RequestMapping("/getinterface")
    public List<Interface> showInterfaceName(@RequestParam String ip){
        return deviceViewService.getInfaceInfo(ip);
    }
    //接口流量信息
    @RequestMapping("/getinterflow")
    public List<InterFlow> showInterflow(@RequestParam String ip,@RequestParam String interName){
        return deviceViewService.getInfaceflow(ip,interName);
    }

    //接口总流量
    @RequestMapping("/getAllInterflow")
    public List<InterFlow> getAllInterflow(@RequestParam String ip){
        return deviceViewService.getAllInfaceflow(ip);
    }

    //过滤vlan
    @RequestMapping("/filtervlan")
    public List<Interface> filtervlan(@RequestParam String ip){
        return deviceViewService.getInfaceInfoFilterVlan(ip);
    }

    //过滤空数据
    @RequestMapping("/filteremptydata")
    public List<Interface> filteremptydata(@RequestParam String ip){
        return deviceViewService.getInfaceInfoFilterEmptydata(ip);
    }
}
