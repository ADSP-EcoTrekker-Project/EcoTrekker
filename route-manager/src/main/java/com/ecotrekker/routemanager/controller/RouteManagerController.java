package com.ecotrekker.routemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.routemanager.model.CalculationErrorResponse;
import com.ecotrekker.routemanager.model.Route;
import com.ecotrekker.routemanager.model.RouteResult;
import com.ecotrekker.routemanager.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/v1")
public class RouteManagerController {
    
    @Autowired
    private RouteService routeService;

    @PostMapping("/calc/route")
    public ResponseEntity<?> calculateCo2(@RequestBody Route route) throws JsonProcessingException {
        RouteResult result = routeService.requestCalculation(route);
        if (result == null) {
            CalculationErrorResponse error = CalculationErrorResponse.builder().error("Invalid Route Data").build();
            return ResponseEntity.status(400).body(error);
        }
        //TODO
        return ResponseEntity.ok(result);
    }

}