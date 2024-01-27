package com.ecotrekker.routemanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CalculationErrorResponse {

    private String error;

}
