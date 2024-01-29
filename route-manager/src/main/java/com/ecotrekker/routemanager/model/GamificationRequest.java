package com.ecotrekker.routemanager.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamificationRequest {
    private List<RouteResult> routes;
    
}
