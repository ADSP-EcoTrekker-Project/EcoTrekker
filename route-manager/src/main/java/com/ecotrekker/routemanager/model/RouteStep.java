package com.ecotrekker.routemanager.model;

import lombok.Data;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;

@Data
@Getter
@AllArgsConstructor
public class RouteStep {
    private String start;
    private String end;
    private String vehicle;
    @JsonIgnore
    private Double distance = null;

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof RouteStep) {
            RouteStep otherStep = (RouteStep) other;
            if (otherStep.getVehicle().equals(this.getVehicle())) {
                if (this.getStart().equals(otherStep.getStart()) && this.getEnd().equals(otherStep.getEnd()) || this.getDistance() == otherStep.getDistance()) {
                    return true;
                }
            }
        }
        return false;
    }
}
