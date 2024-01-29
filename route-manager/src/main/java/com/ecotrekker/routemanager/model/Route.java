package com.ecotrekker.routemanager.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private List<RouteStep> steps;
    private UUID id = UUID.randomUUID();
}
