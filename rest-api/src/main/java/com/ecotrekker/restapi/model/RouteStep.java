package com.ecotrekker.restapi.model;

import com.ecotrekker.restapi.validation.ValidRouteStep;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ValidRouteStep
public class RouteStep {

    private String start;
    private String end;
    private Long distance;
    @NotBlank(message = "Vehicle must not be null or empty!")
    private String vehicle;

}
