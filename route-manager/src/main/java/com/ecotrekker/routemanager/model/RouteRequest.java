package com.ecotrekker.routemanager.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class RouteRequest {

    @Valid
    @NotNull(message = "Routes must not be null")
    private List<Route> routes;
    private boolean gamification = false;

}
