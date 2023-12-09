package com.ecotrekker.restapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RouteStep {

    @NotBlank(message = "start location must not be null or empty!")
    private String start;
    @NotBlank(message = "end location must not be null or empty!")
    private String end;
    @NotBlank(message = "vehicle must not be null or empty!")
    private String vehicle;
    @Positive(message = "distance must be a positive number!")
    private Long distance;

}
