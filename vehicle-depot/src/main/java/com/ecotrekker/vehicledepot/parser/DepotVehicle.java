package com.ecotrekker.vehicledepot.parser;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class DepotVehicle {
    @JsonKey
    private String name;

    @JsonValue
    private Double share;
}
