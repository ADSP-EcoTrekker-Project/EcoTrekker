package com.ecotrekker.co2calculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteStepResult {
    private String start;
    private String end;
    private String vehicle;
    private String line;
    private Double distance;
    private Double co2;
}
