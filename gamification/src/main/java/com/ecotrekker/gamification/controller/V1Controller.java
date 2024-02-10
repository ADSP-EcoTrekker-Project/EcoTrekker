package com.ecotrekker.gamification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.gamification.model.GamificationRequest;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.service.BasePoints;
import com.ecotrekker.gamification.service.Preferred;
import com.ecotrekker.gamification.service.RushHour;

@RestController
@RequestMapping(value = "/v1")
public class V1Controller {

    private final BasePoints basePoints;
    private final Preferred preferred;
    private final RushHour rushHour;

    public V1Controller(BasePoints basePoints, Preferred preferred, RushHour rushHour) {
        this.basePoints = basePoints;
        this.preferred = preferred;
        this.rushHour = rushHour;
    }

    @PostMapping("/calc/points")
    public ResponseEntity<?> calculatePoints(@RequestBody GamificationRequest request) {
        for (Route route : request.getRoutes()) {
            // Calculate base points
            Double points = basePoints.calculatePoints(route);

            // Apply preferred treatment and rush hour modifiers
            points = preferred.applyPreferred(route, points);
            points = rushHour.applyRushHour(route, points);

            // Set the final point value for the route
            route.setPoints(points);
        }

        // Return the modified routes in the response
        return new ResponseEntity<>(request.getRoutes(), HttpStatus.OK);
    }
}