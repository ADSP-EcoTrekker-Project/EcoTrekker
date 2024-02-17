package com.ecotrekker.co2calculator.model;

import java.util.Map;

import lombok.Data;

@Data
public class VehicleDepotResponse {
    private String line;

    private Map<String, Double> vehicles;
}
