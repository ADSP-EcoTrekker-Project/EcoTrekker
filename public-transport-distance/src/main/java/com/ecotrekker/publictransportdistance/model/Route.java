package com.ecotrekker.publictransportdistance.model;

import lombok.Data;

import java.util.List;

@Data
public class Route {
    private String routeId;
    private String routeStart;
    private String routeEnd;
    private List<Stop> stops;
}
