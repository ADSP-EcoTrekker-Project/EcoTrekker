package com.ecotrekker.co2calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsumptionCache {
    private Double share;
    private Double kwh;
    private Double co2;
}