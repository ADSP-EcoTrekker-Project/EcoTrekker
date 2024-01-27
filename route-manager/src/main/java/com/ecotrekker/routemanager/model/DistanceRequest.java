package com.ecotrekker.routemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistanceRequest {
    private RouteStep step;
}
