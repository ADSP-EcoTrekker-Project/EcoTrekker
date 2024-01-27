package com.ecotrekker.routemanager.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GamificationRequest {
    private List<RouteResult> routes;
    
}
