package com.ecotrekker.restapi.model;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Route {

    @Valid
    @NotNull
    @Size(min = 1, message = "At least one step is required in each route.")
    private List<RouteStep> steps;
    private UUID id = UUID.randomUUID();

}
