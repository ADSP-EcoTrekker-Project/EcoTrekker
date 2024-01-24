package com.ecotrekker.vehicledepot.parser;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class DepotVehicle {
    @JsonKey
    public String name;

    @JsonValue
    public Double share;
}
