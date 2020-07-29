package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @date 2020/5/13 -- 17:23
 **/
public interface HomeMapper {

    Integer selectAllSystem();

    Integer selectPoints();

    Integer selectFaultNums();

    List<FaultDistribution> selectFaultDistribution();

    List<Diagnosis> selectNewDiagnosis();

    List<SeriesData> selectEquipmentStatistics();

    List<SeriesData> selectPointsStatistics();

    List<Cpu> selectCpu(@Param("ip") String ip);

    List<Memory> selectMemory(@Param("ip") String ip);
}
