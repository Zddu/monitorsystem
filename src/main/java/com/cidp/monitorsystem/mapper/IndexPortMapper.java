package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.IndexPortRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexPortMapper {
    void addRelate(@Param("list") List<IndexPortRelate> list);
}
