package com.ecotrekker.publictransportdistance.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Route {
    private String routeId;
    private String routeStart;
    private String routeEnd;
    private List<Stop> stops;
    private UUID id = UUID.randomUUID();
}
