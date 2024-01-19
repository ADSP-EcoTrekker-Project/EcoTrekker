package com.ecotrekker.gridco2cache.model;

import lombok.Data;

@Data
public class CarbonIntensityResponse {

    private String zone;
    private double carbonIntensity;
    private String datetime;
    private String updatedAt;
    private String emissionFactorType;
    private boolean isEstimated;
    private String estimationMethod;

}
