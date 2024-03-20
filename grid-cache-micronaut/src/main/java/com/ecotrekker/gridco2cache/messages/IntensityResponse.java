package com.ecotrekker.gridco2cache.messages;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
public class IntensityResponse {
    private Double carbonIntensity;
}
