package com.ecotrekker.co2calculator.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteStep {
    private String start;
    private String end;
    private String vehicle;
    private String line;
    private Double distance = null;
}