package com.ecotrekker.co2calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.co2calculator.model.CalculationErrorResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;
import com.ecotrekker.co2calculator.service.CalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;

    @PostMapping("/calc/co2")
    public ResponseEntity<?> calculateCo2(@RequestBody RouteStep routeStep) throws JsonProcessingException {
        log.info(routeStep.toString());
        try {
            RouteStepResult result = calculatorService.requestCalculation(routeStep);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            e.printStackTrace();
            CalculationErrorResponse error = CalculationErrorResponse.builder().error("Invalid Route Data").build();
            return ResponseEntity.status(400).body(error);
        }       
    }

}