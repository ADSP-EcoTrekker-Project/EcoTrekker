package com.ecotrekker.gridco2cache.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class CarbonIntensityResponse {
    @JsonProperty("co2_per_kwh")
    private int co2_per_kwh;

    public CarbonIntensityResponse(int co2_per_kwh) {
        this.co2_per_kwh = co2_per_kwh;
    }

    public void setCo2_per_kwh(int co2_per_kwh) {
        this.co2_per_kwh = co2_per_kwh;
    }
}
