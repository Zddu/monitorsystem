package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @date 2020/5/13 -- 19:39
 **/
@Data
public class FaultDistribution {
    private String name;//设备名称
    private Integer num;//故障发生次数
}
