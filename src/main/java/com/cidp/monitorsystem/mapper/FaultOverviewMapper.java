package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @date 2020/5/2 -- 11:16
 **/
public interface FaultOverviewMapper {

    List<FaultNums> selectAllFaultNums();

    List<FaultNums> selectAllFaultNumsByTime(@Param("start") String start,@Param("end") String end);

    List<IpFault> selectIpFaultTop10();

    List<IpFault> selectIpFaultTop10ByTime(@Param("start") String start,@Param("end") String end);

    List<Equipment> selectAllEquipment();

    List<Pie> selectSpecifiedDeviceFailuresByIp(@Param("ip") String ip);

    Integer selectSpecifiedDeviceFailuresByIpAccordingtoTime(@Param("ip") String ip,@Param("faultName") String faultName,@Param("start") String start,@Param("end") String end);

    List<String> selectFaultbyIp(@Param("ip") String ip);
}
