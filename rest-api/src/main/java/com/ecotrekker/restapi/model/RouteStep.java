package com.ecotrekker.restapi.model;

import lombok.Data;

@Data
public class RouteStep {
    private String start;
    private String end;
    private String vehicle;
    private Long distance;
}
