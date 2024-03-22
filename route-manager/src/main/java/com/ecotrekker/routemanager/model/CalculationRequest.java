package com.ecotrekker.routemanager.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    private RouteStep step;
    private boolean gamification = false;

    @Override public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false; 
        }
        return this.hashCode() == other.hashCode();
    }

    @Override public int hashCode() { 
        return Objects.hash(step.getVehicle(), step.getLine(), step.getDistance(), gamification);
    }
}
