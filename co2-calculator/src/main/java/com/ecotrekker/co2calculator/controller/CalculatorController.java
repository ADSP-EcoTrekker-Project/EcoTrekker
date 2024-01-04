package com.ecotrekker.co2calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.co2calculator.model.CalculationErrorResponse;
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.service.CalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/v1")
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/calc/co2")
    public ResponseEntity<?> calculateCo2(@RequestBody Route route) throws JsonProcessingException {
        RouteResult result = calculatorService.requestCalculation(route);
        if (result == null) {
            CalculationErrorResponse error = CalculationErrorResponse.builder().error("Invalid Route Data").build();
            return ResponseEntity.status(400).body(error);
        }
        return ResponseEntity.ok(result);
    }

}