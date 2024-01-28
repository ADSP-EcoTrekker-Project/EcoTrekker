package com.ecotrekker.routemanager.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesRequest {

    @Valid
    @NotNull(message = "Routes must not be null")
    private List<Route> routes;
    private boolean gamification = false;

}
