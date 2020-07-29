package com.cidp.monitorsystem.service.dispservice;


import com.cidp.monitorsystem.mapper.FaultOverviewMapper;
import com.cidp.monitorsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date 2020/5/2 -- 11:14
 **/
@Service
public class FaultOverviewService {
    @Autowired
    private FaultOverviewMapper faultOverviewMapper;

    public FaultOverviewTop failuresNums(String time) {
        String start,end=null;
        FaultOverviewTop faultOverviewTop=new FaultOverviewTop();
        if ("".equals(time) || time == null){
            faultOverviewTop.setTop10IpFaults(faultOverviewMapper.selectIpFaultTop10());
            faultOverviewTop.setFaultNums(faultOverviewMapper.selectAllFaultNums());
        }else{
            String[] split = time.split(",");
            start=split[0].trim();
            end=split[1].trim()+" 59:59:59";
            faultOverviewTop.setTop10IpFaults(faultOverviewMapper.selectIpFaultTop10ByTime(start,end));
            faultOverviewTop.setFaultNums(faultOverviewMapper.selectAllFaultNumsByTime(start,end));
        }
        return faultOverviewTop;
    }

    public List<Equipment> getSelectList() {
        return faultOverviewMapper.selectAllEquipment();
    }

    public List<Pie> getPieData(String ip) {
        return faultOverviewMapper.selectSpecifiedDeviceFailuresByIp(ip);
    }

    public List<Series> faultByTimeSlot(String ip) {
        List<Series> result =new ArrayList<>();
        //通过ip查询出该ip出现的故障
        List<String> FaultNames=faultOverviewMapper.selectFaultbyIp(ip);
        //将结果赋值给结果集合并且通过ip和故障名称查询指定故障出现次数
        for (String faultName : FaultNames) {
            Series item =new Series();
            List<Integer> integerList=new ArrayList<>();
            item.setName(faultName);
            item.setLabel(faultName);
            item.setType("bar");
            //根据指定时间ip以及故障名称查询指定故障出现次数
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"08:00:00","12:00:00"));
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"12:00:00","16:00:00"));
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"16:00:00","20:00:00"));
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"20:00:00","24:00:00"));
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"00:00:00","04:00:00"));
            integerList.add(faultOverviewMapper.selectSpecifiedDeviceFailuresByIpAccordingtoTime(ip,faultName,"04:00:00","08:00:00"));


            item.setData(integerList);
            result.add(item);
        }

        return result;
    }
}
