package com.ecotrekker.restapi.model;


import lombok.Data;
import java.util.List;
import java.util.UUID;
@Data
public class RouteResult extends Route {
    private Double co2;
    private Double points;

    public RouteResult(List<RouteStep> steps, UUID id, Double co2) {
        super(steps, id);
        this.co2 = co2;
    }
}
