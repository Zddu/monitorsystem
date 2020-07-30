package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.InterfaceOfMac;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterfaceOfMacMapper {
    void addMac(@Param("list") List<InterfaceOfMac> list);
}
