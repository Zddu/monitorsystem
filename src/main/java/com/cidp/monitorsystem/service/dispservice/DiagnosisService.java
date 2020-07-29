package com.cidp.monitorsystem.service.dispservice;

import com.cidp.monitorsystem.mapper.DiagnosisMapper;
import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import com.cidp.monitorsystem.model.TrapCollect;
import com.cidp.monitorsystem.service.*;
import com.cidp.monitorsystem.util.GetSpecialString;
import com.cidp.monitorsystem.util.ListUtil;
import com.cidp.monitorsystem.util.ip.Ping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 故障管理
 */
@Service
public class DiagnosisService {
    @Autowired
    SystemService systemService;
    @Autowired
    ThreadPingSuccess ping;
    @Autowired
    DiagnosisMapper diagnosisMapper;
    @Autowired
    ThreadSnmpService snmpService;
    @Autowired
    TrapCollectService trapService;
    @Autowired
    PointService pointService;
    @Autowired
    MibsService mibsService;

    /**
     * ping连通性监测
     */
    public void checkPing() throws Exception {
        List<String> ips = systemService.getIps();
        List<String> list = ping.receiveConnectSuccess(ips);
        List<String> noping = ListUtil.getDiffrent(ips, list);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (String ip : noping) {
            Diagnosis d = new Diagnosis();
            String sping = Ping.sping(ip, 2, 1000);
            d.setIp(ip);
            d.setStatus(0);//未处理
            d.setPid(5);//ping
            d.setRank("故障");
            d.setTime(df.format(new Date()));
            d.setCause(sping.substring(sping.indexOf("。")).replace("。", "").trim());
            diagnosisMapper.insertByDia(d);
        }


    }

    /**
     * 能ping通下
     * 监测snmp连通性
     */

    public void checkSnmp() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> ips = systemService.getIps();
        List<String> list = ping.receiveConnectSuccess(ips);
        List<String> ckSnmp = snmpService.IsCkSnmp(list);
        for (String s : ckSnmp) {
            Diagnosis d = new Diagnosis();
            d.setIp(s);
            d.setStatus(0);//未处理
            d.setPid(6);//snmp
            d.setTime(df.format(new Date()));
            d.setRank("故障");
            d.setCause("Ping Success,SNMP connect failure！");
            diagnosisMapper.insertByDia(d);
        }
    }

    /**
     * 解析trap消息
     */
    public void checkTrap() {
        List<TrapCollect> ipAndVal = trapService.getIpAndVal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TrapCollect i : ipAndVal) {
            Diagnosis d = new Diagnosis();
            d.setIp(i.getIp());
            d.setStatus(0);
            d.setPid(13);
            d.setCause(i.getValue());
            d.setTime(df.format(new Date()));
            diagnosisMapper.insertByDia(d);
        }
    }
    public List<Diagnosis> getPageInfoByStatus(Integer status) {
        //获取聚合过的ip和异常名称
        List<Diagnosis> info = diagnosisMapper.getPageInfoByStatus(status);

        for (Diagnosis diagnosis : info) {
            //查询出最新的数据
            List<Diagnosis> lists = diagnosisMapper.selectDiagnosisByIPAndzhtype(diagnosis.getIp(),diagnosis.getCheck().getZhtype());
            //插入最新时间
            diagnosis.setNewTime(lists.get(0).getTime());
            //插入最开始出现的时间
            diagnosis.setIp(lists.get(0).getIp());
            diagnosis.setTime(lists.get(lists.size()-1).getTime());
            diagnosis.setCause(lists.get(0).getCause());
            diagnosis.setRank(lists.get(0).getRank());
            diagnosis.setCheck(lists.get(0).getCheck());
            diagnosis.setStatus(lists.get(0).getStatus());
            diagnosis.setId(lists.get(0).getId());
            diagnosis.setFrequency(lists.size());
        }
        return  info;
    }
    public Integer updateRemark( String desc, String id) {
        //通过id出指定的ip以及异常点
        Diagnosis diagnosis =diagnosisMapper.selectDiagnosisByID(id);

        if (diagnosis==null){
            return -1;
        }
        //设置状态为修改
        List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));

        return diagnosisMapper.updateProcessStatus(desc, GetSpecialString.getCommaSeparated(idlist));
    }
    @Transactional
    public Integer updateSelectRemark(String desc, String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            Diagnosis diagnosis =diagnosisMapper.selectDiagnosisByID(id);
            if (diagnosis==null){
                return -1;
            }
            List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));

            result=diagnosisMapper.updateProcessStatus(desc,GetSpecialString.getCommaSeparated(idlist));
        }
        return result;
    }

    public Integer ignoreById( String id) {
        Diagnosis diagnosis =diagnosisMapper.selectDiagnosisByID(id);
        if (diagnosis==null){
            return -1;
        }
        List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));
        return diagnosisMapper.updateIgnoreStatus(GetSpecialString.getCommaSeparated(idlist));
    }
    @Transactional
    public Integer checkIgnoreById(String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            Diagnosis diagnosis = diagnosisMapper.selectDiagnosisByID(id);
            if (diagnosis==null){
                return -1;
            }
            List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));
            result = diagnosisMapper.updateIgnoreStatus(GetSpecialString.getCommaSeparated(idlist));
        }
        return result;
    }

    public Integer deleteById(String id) {
        Diagnosis diagnosis = diagnosisMapper.selectDiagnosisByID(id);
        if (diagnosis==null){
            return -1;
        }
        List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));
        return diagnosisMapper.deleteByInforAndStatus(GetSpecialString.getCommaSeparated(idlist));
    }
    @Transactional
    public Integer checkDeleteById(String[] ids) {
        Integer result=-1;
        for (String id : ids) {
            Diagnosis diagnosis = diagnosisMapper.selectDiagnosisByID(id);
            if (diagnosis==null){
                return -1;
            }
            List<String> idlist= diagnosisMapper.selectIdsByIpDiagnosisAndTime(diagnosis.getIp(),diagnosis.getCheck().getZhtype(),diagnosis.getTime(),String.valueOf(diagnosis.getStatus()));
            result = diagnosisMapper.deleteByInforAndStatus(GetSpecialString.getCommaSeparated(idlist));
        }
        return result;
    }






    public String getRemark(String id) {
        return diagnosisMapper.getRemark(id);
    }



    public List<ExportDiagnosis> selectDiagnosisInfo(Integer status) {
        List<ExportDiagnosis> result =new ArrayList<>();
        //按状态分组
        List<Diagnosis> info = diagnosisMapper.getPageInfoByStatus(status);
        for (Diagnosis diagnosis : info) {
            //查询出最新的数据
            ExportDiagnosis item =new ExportDiagnosis();
            List<Diagnosis> lists = diagnosisMapper.selectDiagnosisByIPAndzhtype(diagnosis.getIp(),diagnosis.getCheck().getZhtype());
            //插入ip
            item.setIp(lists.get(0).getIp());
            //插入最新时间
            item.setNewTime(lists.get(0).getTime());
            //插入最开始出现的时间
            item.setTime(lists.get(lists.size()-1).getTime());
            item.setCause(lists.get(0).getCause());
            item.setRank(lists.get(0).getRank());
            item.setZhtype(lists.get(0).getCheck().getZhtype());
            item.setStatus(lists.get(0).getStatus());
            item.setId(lists.get(0).getId());
            item.setFrequency(lists.size());
            result.add(item);
        }
        return result;
    }






}
