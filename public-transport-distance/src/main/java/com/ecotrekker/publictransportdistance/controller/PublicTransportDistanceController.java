package com.ecotrekker.publictransportdistance.controller;

import java.util.NoSuchElementException;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import com.ecotrekker.publictransportdistance.service.PublicTransportDistanceService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/calc")
@Slf4j
public class PublicTransportDistanceController {

    @Autowired
    private PublicTransportDistanceService distanceService;


    @PostMapping("/distance")
    public ResponseEntity<?> calculateDistance(@RequestBody DistanceRequest request) {
        long startDate  = System.nanoTime();
        try{
            RouteStep step = request.getStep();
            String start = step.getStart();
            String end = step.getEnd();
            String line = step.getLine();

            double distance = distanceService.calculateDistance(start, end, line);
            DistanceResponse response = new DistanceResponse(distance);
            log.info("[DISTANCE] Time taken to calculate distance: " + (System.nanoTime() - startDate) + "ns");
            return ResponseEntity.ok(response);
        } catch ( NoSuchElementException e ) {
            log.info("[DISTANCE] Time taken to find no such argument: " + (System.nanoTime() - startDate) + "ns");
            return ResponseEntity.notFound().build();
        } catch ( IllegalArgumentException e ) {
            log.info("[DISTANCE] Time taken to detect illegal argument: " + (System.nanoTime() - startDate) + "ns");
            return ResponseEntity.badRequest().build();
        } catch ( Exception e ) {
            log.error(e.getMessage());
            log.info("[DISTANCE] Time taken to find internal error: " + (System.nanoTime() - startDate) + "ns");
            return ResponseEntity.internalServerError().build();
        }
        
    }
}
