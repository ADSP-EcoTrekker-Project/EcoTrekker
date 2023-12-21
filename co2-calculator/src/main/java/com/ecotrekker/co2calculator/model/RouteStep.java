package com.ecotrekker.co2calculator.model;

import lombok.Data;

@Data
public class RouteStep {
    private String start;
    private String end;
    private String vehicle;
    private Long distance;
}
