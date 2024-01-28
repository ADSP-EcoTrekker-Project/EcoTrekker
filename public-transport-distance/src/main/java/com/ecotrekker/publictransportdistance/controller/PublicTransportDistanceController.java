package com.ecotrekker.publictransportdistance.controller;

import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.ecotrekker.publictransportdistance.service.PublicTransportDistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/public-transport")
public class PublicTransportDistanceController {

    private final PublicTransportDistanceService distanceService;

    @Autowired
    public PublicTransportDistanceController(PublicTransportDistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @PostMapping("/distance")
    public ResponseEntity<DistanceResponse> calculateDistance(@RequestBody RouteStep step) {
        String start = step.getStart();
        String end = step.getEnd();
        String vehicle = step.getVehicle();

        double distance = distanceService.calculateDistance(start, end, vehicle);
        DistanceResponse response = new DistanceResponse(distance);

        return ResponseEntity.ok(response);
    }
}
