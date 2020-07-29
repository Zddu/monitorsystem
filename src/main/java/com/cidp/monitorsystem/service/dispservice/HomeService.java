package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.HomeMapper;
import com.cidp.monitorsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页 Service
 */
@Service
public class HomeService {
    @Autowired
    private HomeMapper homeMapper;
    public InformationStatistics getInformationStatistics() {
        InformationStatistics result =new InformationStatistics();
        result.setEquipmentNums(homeMapper.selectAllSystem());
        result.setPointsNums(homeMapper.selectPoints());
        result.setFaultNums(homeMapper.selectFaultNums());
        return result;
    }

    public List<FaultDistribution> getFaultDistribution() {
        return homeMapper.selectFaultDistribution();
    }

    public  List<Diagnosis> getNewDiagnosis() {
        return homeMapper.selectNewDiagnosis();
    }

    public List<SeriesData> getEquipmentStatistics() {
        return homeMapper.selectEquipmentStatistics();
    }

    public List<SeriesData> getPointsStatistics() {
        return homeMapper.selectPointsStatistics();
    }

    public List<Cpu> getCpu() {
        return homeMapper.selectCpu("172.17.137.31");
    }

    public List<Memory> getMemory() {
        return homeMapper.selectMemory("172.17.137.31");
    }
}
