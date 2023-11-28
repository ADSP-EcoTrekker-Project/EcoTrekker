package com.ecotrekker.restapi.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Route {
    private List<RouteStep> steps;
    private UUID id = UUID.randomUUID();
}
