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

    private Double car_ratio = 6D;

    private Double calculate(Double co2, Double distance) {
        Double ratio = Math.max(1, 
            ((distance / co2) - car_ratio));
        Double result = Math.log10(1 + ratio) * distance;
        return result;
    }

    public Double calculatePoints(Route route) {
        Double distance = 0D;
        for (Step step : route.getSteps()) {
            distance += step.getDistance();
        }
        Double co2 = route.getCo2();
        return calculate(co2, distance);
    }
}
