package com.ecotrekker.restapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class Routes {

    @Valid
    @NotNull(message = "Routes must not be null")
    private List<Route> routes;

}
