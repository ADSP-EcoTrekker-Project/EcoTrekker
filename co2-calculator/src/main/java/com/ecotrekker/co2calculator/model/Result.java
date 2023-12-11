package com.ecotrekker.co2calculator.model;

import lombok.Data;
import java.util.List;

@Data
public class Result {
    private List<RouteResult> routes;
}
