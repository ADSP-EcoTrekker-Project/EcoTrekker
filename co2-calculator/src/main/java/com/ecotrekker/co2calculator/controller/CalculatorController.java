package com.ecotrekker.co2calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.co2calculator.model.CalculationErrorResponse;
import com.ecotrekker.co2calculator.model.CalculationErrorResponseBuilder;
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.service.CalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/v1")
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/calc/co2")
    public ResponseEntity<?> calculateCo2(@RequestBody Route route) throws JsonProcessingException {
        RouteResult resultJSON = calculatorService.requestCalculation(route);
        if (resultJSON == null) {
            CalculationErrorResponse errorResponse = (new CalculationErrorResponseBuilder()).setErrorMessage("Invalid Route Data").build();
            return ResponseEntity.status(400).body(objectMapper.writeValueAsString(errorResponse));
        }
        return ResponseEntity.ok(resultJSON);
    }

}