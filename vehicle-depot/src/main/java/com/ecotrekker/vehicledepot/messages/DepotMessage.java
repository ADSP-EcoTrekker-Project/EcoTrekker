package com.ecotrekker.vehicledepot.messages;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DepotMessage {
    private String line;

    @JsonProperty(required = false)
    private Map<String, Double> vehicles;
}
