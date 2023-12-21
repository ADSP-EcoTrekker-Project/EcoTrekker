package com.ecotrekker.co2calculator.model;

import lombok.Data;

@Data
public class ConsumptionResponse {
    String vehicle;
    Double consum_kwh_m = null;
    Double co2_per_m = null;
}
