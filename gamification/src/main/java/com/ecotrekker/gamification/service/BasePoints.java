package com.ecotrekker.gamification.service;

import org.springframework.stereotype.Service;

import com.ecotrekker.gamification.model.GamificationRequest;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;

/*
 * Contains the business logic to calculate the base points
 */
@Service
public class BasePoints {

    private Double calculate(Double co2, Double distance) {
        Double result = distance / co2;
        return result;
    }

    public void calculatePoints(GamificationRequest request) {
        for (Route route : request.getRoutes()) {
            Double distance = 0D;
            for (Step step : route.getSteps()) {
                distance += step.getDistance();
            }
            Double co2 = route.getCo2();
            route.setPoints(calculate(co2, distance));
        }
    }
    
}
