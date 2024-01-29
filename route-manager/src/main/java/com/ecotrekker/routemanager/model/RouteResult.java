package com.ecotrekker.routemanager.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RouteResult extends Route{
    private Double co2;

    public RouteResult(List<RouteStep> steps, UUID id, Double co2) {
        super(steps, id);
        this.co2 = co2;
    }
}
