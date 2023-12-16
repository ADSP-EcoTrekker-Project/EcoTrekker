package com.ecotrekker.gridco2cache.controller;

import com.ecotrekker.gridco2cache.service.ElectricityMapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co2")
@RequiredArgsConstructor
public class ElectricityMapsController {
    private final ElectricityMapsService electricityMapsService;
    @GetMapping("/get-germany-carbon-intensity")
    public ResponseEntity<?> callEndpointToGetGermanyCarbonIntensity() {
        return ResponseEntity.ok(electricityMapsService.getGermanyCarbonIntensity());

    }
}
