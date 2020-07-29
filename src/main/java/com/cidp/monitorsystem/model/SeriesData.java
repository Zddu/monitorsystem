package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * echarts 图表插件 中饼状图的data数据格式
 */
@Data
public class SeriesData {
    private Integer value; //设备数量/监测点故障次数
    private String name;//设备名称/监测点故障名称
}
