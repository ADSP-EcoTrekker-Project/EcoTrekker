package com.ecotrekker.co2calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDepotRequest {
    private String line;

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof VehicleDepotRequest) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return line.hashCode();
    }
}
