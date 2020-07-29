package com.cidp.monitorsystem.mapper;

import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiagnosisMapper {
    void insert(@Param("list") List<Diagnosis> list);

    void insertByDia(Diagnosis d);

    List<Diagnosis> getPageInfoByStatus(Integer status);

    Integer updateRemark(@Param("desc") String desc, @Param("id") String id);

    String getRemark(@Param("id") String id);

    List<Diagnosis> selectDiagnosisByIPAndzhtype(@Param("ip") String ip,@Param("zhtype") String zhtype);

    Diagnosis selectDiagnosisByID(@Param("id") String id);

    Integer updateProcessStatus(@Param("desc") String desc,@Param("ids") String ids);

    Integer updateIgnoreStatus(@Param("ids") String ids);

    Integer deleteByInforAndStatus(@Param("ids") String ids);

    List<String> selectIdsByIpDiagnosisAndTime(@Param("ip") String ip,@Param("zhtype") String zhtype,@Param("time") String time,@Param("status") String status);
}
