package com.ecotrekker.gridco2cache.controller;

import com.ecotrekker.gridco2cache.service.CarbonIntensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarbonIntensityController {

    private final CarbonIntensityService carbonIntensityService;

    @Autowired
    public CarbonIntensityController(CarbonIntensityService carbonIntensityService) {
        this.carbonIntensityService = carbonIntensityService;
    }

    @GetMapping("/carbon-intensity")
    public ResponseEntity<Double> getCarbonIntensity() {
        Double carbonIntensity = carbonIntensityService.getLatestCarbonIntensity();

        if (carbonIntensity == null) {
            // Fetch from API if not available in cache
            carbonIntensity = carbonIntensityService.getCarbonIntensityForZone("DE");
        }

        return ResponseEntity.ok(carbonIntensity);
    }


}