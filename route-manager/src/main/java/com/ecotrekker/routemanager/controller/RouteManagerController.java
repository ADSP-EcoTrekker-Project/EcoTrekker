package com.ecotrekker.routemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.routemanager.model.CalculationErrorResponse;
import com.ecotrekker.routemanager.model.RouteRequest;
import com.ecotrekker.routemanager.model.RouteServiceException;
import com.ecotrekker.routemanager.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/v1")
public class RouteManagerController {
    
    @Autowired
    private RouteService routeService;

    @PostMapping("/calc/routes")
    public ResponseEntity<?> calculateRouteData(@RequestBody RouteRequest routeRequest) throws JsonProcessingException {
        try {
            return ResponseEntity.ok(routeService.requestCalculation(routeRequest));
        } catch (RouteServiceException e) {
            return ResponseEntity
            .status(400)
            .body(CalculationErrorResponse.builder().error(e.getMessage()).build());
        }
    }

}