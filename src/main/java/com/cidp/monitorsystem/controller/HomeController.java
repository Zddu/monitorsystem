package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.*;
import com.cidp.monitorsystem.service.dispservice.HomeService;
import com.sun.xml.internal.ws.wsdl.writer.document.Fault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.RequestWrapper;
import java.util.List;

/**
 * 首页controller
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private HomeService homeService;
    //全网信息统计
    @GetMapping("/getInformationStatistics")
    public InformationStatistics getInformationStatistics(){
        return homeService.getInformationStatistics();
    }

    //故障分布
    @GetMapping("/getFaultDistribution")
    public List<FaultDistribution> getFaultDistribution(){
        return homeService.getFaultDistribution();
    }

    //故障细则
    @GetMapping("/getNewDiagnosis")
    public List<Diagnosis> getNewDiagnosis(){
        return homeService.getNewDiagnosis();
    }

    //设备统计
    @GetMapping("/getEquipmentStatistics")
    public List<SeriesData> getEquipmentStatistics(){
        return homeService.getEquipmentStatistics();
    }

    //监测点统计
    @GetMapping("/getPointsStatistics")
    public List<SeriesData> getPointsStatistics(){
        return homeService.getPointsStatistics();
    }

    //本机CPU率
    @GetMapping("/getCpu")
    public List<Cpu> getCpu(){
        return  homeService.getCpu();
    }

    //本机内存使用率
    @GetMapping("/getMemory")
    public List<Memory> getMemory(){
        return  homeService.getMemory();
    }
}
