package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.NeighborMac;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NeighborMacMapper {
    void addRemMac(@Param("list") List<NeighborMac> list);

    List<String> getRemMac(String ip);
}
