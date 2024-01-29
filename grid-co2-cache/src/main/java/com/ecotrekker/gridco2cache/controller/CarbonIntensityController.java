package com.ecotrekker.gridco2cache.controller;

import com.ecotrekker.gridco2cache.model.CarbonResponse;
import com.ecotrekker.gridco2cache.service.CarbonIntensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarbonIntensityController {

    @Autowired
    private CarbonIntensityService carbonIntensityService;

    @GetMapping("/carbon-intensity")
    public ResponseEntity<?> getCarbonIntensity() {
        Double carbonIntensity = carbonIntensityService.getLatestCarbonIntensity();
        CarbonResponse response = new CarbonResponse();
        response.setCarbonIntensity(carbonIntensity);
        return ResponseEntity.ok(response);
    }
}