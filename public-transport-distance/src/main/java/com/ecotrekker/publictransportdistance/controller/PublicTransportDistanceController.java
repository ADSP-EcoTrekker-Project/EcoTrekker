package com.ecotrekker.publictransportdistance.controller;

import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.service.PublicTransportDistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/public-transport")
public class PublicTransportDistanceController {

    @Autowired
    private PublicTransportDistanceService distanceService;


    @PostMapping("/distance")
    public ResponseEntity<DistanceResponse> calculateDistance(@RequestBody RouteStep step) {
        String start = step.getStart();
        String end = step.getEnd();
        String line = step.getLine();

        double distance = distanceService.calculateDistance(start, end, line);
        DistanceResponse response = new DistanceResponse(distance);

        return ResponseEntity.ok(response);
    }
}
