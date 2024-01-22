package com.ecotrekker.vehicledepot.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DepotMessage {
    private String line;

    @JsonProperty(value = "share-electrical", required = false)
    private Double shareElectrical;
}
