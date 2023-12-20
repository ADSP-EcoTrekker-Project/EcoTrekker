package com.ecotrekker.co2calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CalculatorService {

    private final ObjectMapper objectMapper;

    @Autowired
    public CalculatorService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String requestCalculation(Route routes) throws JsonProcessingException {
        RouteResult result = new RouteResult();
        result.setCo2(999d);
        return objectMapper.writeValueAsString(result);
    }
}