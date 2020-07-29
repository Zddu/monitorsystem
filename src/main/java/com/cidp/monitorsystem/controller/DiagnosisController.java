package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import com.cidp.monitorsystem.model.RespBean;
import com.cidp.monitorsystem.service.dispservice.DiagnosisService;
import com.cidp.monitorsystem.util.ExcellUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {
    @Autowired
    DiagnosisService diagnosisService;
    //返回未处理警告
    @GetMapping("/unprocessedpage")
    public List<Diagnosis> getUntreatedPageInfo(){
        return diagnosisService.getPageInfoByStatus(0);
    }


    //返回已处理警告
    @GetMapping("/processedpage")
    public List<Diagnosis> getProcessedPageInfo(){
        return diagnosisService.getPageInfoByStatus(1);
    }


    //返回已忽略警告
    @GetMapping("/ignorepage")
    public List<Diagnosis> getIgnorePageInfo(){
        return diagnosisService.getPageInfoByStatus(2);
    }

    //获取备注信息
    @GetMapping("/remark/{id}")
    public String getRemark(@PathVariable String id){
        return diagnosisService.getRemark(id);
    }

    //单个处理
    @PutMapping("/process")
    public RespBean remark(@RequestParam String desc,@RequestParam String id){
        return diagnosisService.updateRemark(desc,id)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //选中处理多个
    @PutMapping("/check/process")
    public RespBean updateSelectRemark(@RequestParam String desc,@RequestParam String[] ids){
       return diagnosisService.updateSelectRemark(desc,ids)==ids.length ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //单个忽略
    @PutMapping("/ignore")
    public RespBean ignore(@RequestParam String id){
        return diagnosisService.ignoreById(id)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //选中忽略多个
    @PutMapping("/check/ignore")
    public RespBean checkIgnore(@RequestParam String[] ids){
        return diagnosisService.checkIgnoreById(ids)==ids.length ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //单个删除
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam String id){
        return diagnosisService.deleteById(id)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //选中删除多个
    @DeleteMapping("/check/delete")
    public RespBean checkDelete(@RequestParam String[] ids){
        return diagnosisService.checkDeleteById(ids)==ids.length ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //导出未处理警告
    @GetMapping("/export/unhandledwarnings")
    public void exportUnhandledWarnings(HttpServletResponse response){

        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectDiagnosisInfo(0);

        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("newTime","最新时间");
        headMap.put("frequency","出现次数");
        headMap.put("cause","详细信息");
        String title = "未处理警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }
    //导出正在处理警告
    @GetMapping("/export/processedwarnings")
    public void exportProcessedWarnings(HttpServletResponse response){
        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectDiagnosisInfo(1);
        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("newTime","最新时间");
        headMap.put("frequency","出现次数");
        headMap.put("cause","详细信息");
        String title = "正在处理处理警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }

    //导出已已忽略警告
    @GetMapping("/export/ignorewarnings")
    public void exportIgnoreWarnings(HttpServletResponse response){
        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectDiagnosisInfo(2);
        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("newTime","最新时间");
        headMap.put("frequency","出现次数");
        headMap.put("cause","详细信息");
        String title = "已忽略警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }

}
