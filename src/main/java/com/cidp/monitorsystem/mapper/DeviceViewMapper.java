package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.*;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @date 2020/5/8 -- 7:54
 **/
public interface DeviceViewMapper {


    List<Memory> selectMemoryByIp(@Param("ip")String ip);

    List<Interface> selectInterfaceByIp(@Param("ip") String ip);

    List<InterFlow> selectInterfaceflowByInter(@Param("ip") String ip,@Param("interDescr") String interDescr);

    List<Cpu> selectCupByIp(@Param("ip") String ip);

    List<SystemInfo> getDevice();

    List<InterFlow> selecAllInfaceFlow(@Param("ip") String ip);

    List<Interface> selectInterfaceFilterVlanByIp(@Param("ip") String ip);

    List<Interface> selectInfaceInfoFilterEmptydata(@Param("ip") String ip);
}
