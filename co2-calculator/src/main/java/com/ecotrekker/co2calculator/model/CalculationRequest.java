package com.ecotrekker.co2calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequest {
    private RouteStep step;
    private boolean gamification = false;
}
