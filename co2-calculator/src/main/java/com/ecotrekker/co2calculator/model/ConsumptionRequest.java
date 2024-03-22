package com.ecotrekker.co2calculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionRequest {
    private String vehicle;

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof ConsumptionRequest) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vehicle.hashCode();
    }
}
