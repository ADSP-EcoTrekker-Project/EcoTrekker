package com.ecotrekker.publictransportdistance.controller;

import java.util.NoSuchElementException;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import com.ecotrekker.publictransportdistance.service.PublicTransportDistanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/calc")
public class PublicTransportDistanceController {

    @Autowired
    private PublicTransportDistanceService distanceService;


    @PostMapping("/distance")
    public ResponseEntity<?> calculateDistance(@RequestBody DistanceRequest request) {
        try{
            RouteStep step = request.getStep();
            String start = step.getStart();
            String end = step.getEnd();
            String line = step.getLine();

            double distance = distanceService.calculateDistance(start, end, line);
            DistanceResponse response = new DistanceResponse(distance);

            return ResponseEntity.ok(response);
        } catch ( NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch ( IllegalArgumentException e ) {
            return ResponseEntity.badRequest().build();
        } catch ( Exception e ) {
            return ResponseEntity.internalServerError().build();
        }
        
    }
}
