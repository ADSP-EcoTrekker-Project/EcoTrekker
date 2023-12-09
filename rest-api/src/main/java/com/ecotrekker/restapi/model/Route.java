package com.ecotrekker.restapi.model;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class Route {

    @Valid
    private List<RouteStep> steps;
    private UUID id = UUID.randomUUID();

}
