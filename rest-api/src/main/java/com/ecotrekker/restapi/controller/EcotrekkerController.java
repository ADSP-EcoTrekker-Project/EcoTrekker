package com.ecotrekker.restapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.ecotrekker.restapi.model.RoutesRequest;
import com.ecotrekker.restapi.model.RoutesResult;
import com.ecotrekker.restapi.service.EcotrekkerService;

@Validated
@RestController
@RequestMapping(value = "/v1")
public class EcotrekkerController {

    private final EcotrekkerService ecotrekkerService;

    @Autowired
    public EcotrekkerController(EcotrekkerService ecotrekkerService) {
        this.ecotrekkerService = ecotrekkerService;
    }

    @PostMapping("/calc/co2")
    public ResponseEntity<RoutesResult> calculateCo2(@RequestBody @Valid RoutesRequest routes) throws JsonProcessingException {
        RoutesResult resultJSON = ecotrekkerService.requestCalculation(routes);
        if (resultJSON != null) { return ResponseEntity.ok(resultJSON); }
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
    }

}