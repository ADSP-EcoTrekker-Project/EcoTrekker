package com.ecotrekker.routemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResponse {
    private RouteStepResult result;
    private Double points = null;
}
