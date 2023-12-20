package com.ecotrekker.restapi.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Data
public class Route {

    @Valid
    @NotNull(message = "Route must not be null")
    @Size(min = 1, message = "At least one step is required in each route.")
    @Getter
    private List<RouteStep> steps;
    
    private UUID id = UUID.randomUUID();

}
