package com.ecotrekker.restapi.model;

import lombok.Data;
import java.util.List;

@Data
public class Result {
    private List<RouteResult> routes;
}
