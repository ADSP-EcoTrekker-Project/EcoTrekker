package com.ecotrekker.gamification.model;

import lombok.Data;

@Data
public class Step {
    private String start;
    private String end;
    private String vehicle;
    private String line;
    private Double distance = null;

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Step) {
            Step otherStep = (Step) other;
            if (otherStep.getVehicle().equals(this.getVehicle())) {
                if (this.getStart().equals(otherStep.getStart()) && this.getEnd().equals(otherStep.getEnd()) || this.getDistance() == otherStep.getDistance()) {
                    return true;
                }
            }
        }
        return false;
    }
}
