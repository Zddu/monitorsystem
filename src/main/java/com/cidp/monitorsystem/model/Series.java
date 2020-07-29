package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class Series {
    private String name;
    private String type;
    private String label;
    private List<Integer> data;
}
