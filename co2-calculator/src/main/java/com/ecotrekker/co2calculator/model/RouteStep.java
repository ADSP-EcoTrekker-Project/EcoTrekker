package com.ecotrekker.co2calculator.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.StringUtils;

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

    public boolean isTopLevel() {
        return (
            (StringUtils.countOccurrencesOf(vehicle, "/") <= 0) ||
            ((StringUtils.countOccurrencesOf(vehicle, "/") == 1) && vehicle.startsWith("/"))
        );
    }
}