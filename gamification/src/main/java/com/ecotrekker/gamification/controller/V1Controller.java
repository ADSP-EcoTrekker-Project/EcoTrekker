package com.ecotrekker.gamification.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

/**
 * Controller class that handles HTTP requests for the /v1 endpoint.
 * It contains methods to calculate points for a set of routes.
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class V1Controller {

    @Autowired
    private Preferred preferred;
    @Autowired
    private RushHour rushHour;

    /**
     * Handles a POST request to the /calc/points endpoint.
     * This method calculates the points for each route in the given GamificationRequest.
     * The points are calculated based on the CO2 emissions and distance of the route, with preferential treatment and
     * rush hour factors applied.
     * The calculated points are set in the points field of each route.
     *
     * @param request The GamificationRequest containing the routes to calculate points for.
     * @return A ResponseEntity containing the routes with the calculated points and a status code of 200 OK.
     */
    @PostMapping("/calc/points")
    public ResponseEntity<?> calculatePoints(@RequestBody GamificationRequest request) {
        log.info(request.toString());
        for (Route route : request.getRoutes()) {
            // calculate base points
            BasePoints basePoints = new BasePoints();
            basePoints.calculatePoints(route);
            log.info(basePoints.toString());

            // apply preferred treatment and rush hour modifiers
            Double newPoints = 0D;
            newPoints = preferred.applyPreferred(route, basePoints);
            log.info(newPoints.toString());
            newPoints = rushHour.applyRushHour(newPoints);
            log.info(newPoints.toString());

            // set the final point value for the route
            route.setPoints(newPoints);
        }

        // return the modified routes in the response
        return new ResponseEntity<GamificationRequest>(request, HttpStatus.OK);
    }
}