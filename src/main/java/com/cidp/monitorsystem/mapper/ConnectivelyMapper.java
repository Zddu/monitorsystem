package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Connectively;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConnectivelyMapper {
    List<Connectively> getConnect();

    Connectively hsaSip2Dip(@Param("sip") String sip, @Param("dip") String dip);
    Connectively hasDip2Sip(@Param("dip") String dip,@Param("sip") String sip);

    void addConnect(Connectively connectively);

    List<Connectively> getTrimConnect();
}
