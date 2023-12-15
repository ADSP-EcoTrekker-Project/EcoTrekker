package com.ecotrekker.restapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class Routes {

    @Valid
    @NotNull
    private List<Route> routes;

}
