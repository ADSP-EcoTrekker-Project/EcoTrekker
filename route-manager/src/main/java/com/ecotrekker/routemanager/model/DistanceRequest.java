package com.ecotrekker.routemanager.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistanceRequest {
    private RouteStep step;

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
        return Objects.hash(step.getStart(), step.getEnd(), step.getLine());
    }
    
}
