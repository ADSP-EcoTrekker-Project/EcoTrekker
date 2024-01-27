package com.ecotrekker.routemanager.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RouteStepResult {
    private String start;
    private String end;
    private String vehicle;
    private Double distance;
    private Double co2;
}
