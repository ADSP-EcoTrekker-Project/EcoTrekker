package com.ecotrekker.publictransportdistance.model;

import lombok.Data;

import java.util.List;

@Data
public class VehicleRoute {
    private String lineName;
    private String lineProduct;
    private List<String> routeIds;
    private List<Route> routes;
}
