package com.ecotrekker.restapi.model;

import com.ecotrekker.restapi.validation.ValidRouteStep;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ValidRouteStep
public class RouteStep {

    private String start;
    private String end;
    @NotBlank(message = "Vehicle must not be null or empty!")
    private String vehicle;
    private String line;
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