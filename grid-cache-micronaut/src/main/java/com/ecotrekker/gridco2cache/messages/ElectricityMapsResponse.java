package com.ecotrekker.gridco2cache.messages;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class ElectricityMapsResponse {
    
    private String zone;
    private double carbonIntensity;
    private String datetime;
    private String updatedAt;
    private String emissionFactorType;
    private boolean isEstimated;
    private String estimationMethod;

}
