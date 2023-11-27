package com.ecotrekker.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.service.EcotrekkerService;

@RestController
@RequestMapping(value = "/v1")
public class EcotrekkerController {
    private final EcotrekkerService ecotrekkerService;

    @Autowired
    public EcotrekkerController(EcotrekkerService ecotrekkerService) {
        this.ecotrekkerService = ecotrekkerService;
    }

    @PostMapping("/calc/co2")
    public String calculateCo2(@RequestBody Routes routes) throws JsonProcessingException {
        return ecotrekkerService.requestCalculation(routes);
    }

}