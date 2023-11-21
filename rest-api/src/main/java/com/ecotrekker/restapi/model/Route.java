package com.ecotrekker.restapi.model;

import java.util.List;
import lombok.Data;

@Data
public class Route {
    private List<RouteStep> steps;
}
