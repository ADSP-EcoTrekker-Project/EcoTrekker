package com.ecotrekker.co2calculator.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionResponse {

    @JsonProperty("vehicle")
    @JsonAlias({ "vehicle", "vehicle-name", "vehicle_name", "name", "vehicleName" })
    private String vehicle;

    @JsonProperty("consum_kwh_m")
    @JsonAlias({ "kwh", "consumption", "consum_kwh_m", "consumptionKwhM" })
    private Double kwh;

    @JsonProperty("co2_per_m")
    @JsonAlias({ "emissions", "co2", "co2_per_m", "co2M" })
    private Double co2;
}
