package com.ecotrekker.co2calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.service.CalculatorService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/v1")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calc/co2")
    public ResponseEntity<String> calculateCo2(@RequestBody Route route) throws JsonProcessingException {
        String resultJSON = calculatorService.requestCalculation(route);
        return ResponseEntity.ok(resultJSON);
    }

}