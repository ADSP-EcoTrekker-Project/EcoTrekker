package com.ecotrekker.restapi.model;

import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;

@Data
public class Routes {

    @Valid
    private List<Route> routes;

}
