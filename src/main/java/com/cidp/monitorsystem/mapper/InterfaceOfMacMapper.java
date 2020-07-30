package com.cidp.monitorsystem.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface InterfaceOfMacMapper {
    void addMac(@Param("list") ArrayList<String> list);
}
