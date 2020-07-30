package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.MacForward;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MacForwardMapper {

    void addMac(@Param("list") List<MacForward> list);
}
