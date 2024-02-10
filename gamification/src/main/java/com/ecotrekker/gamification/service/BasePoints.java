package com.ecotrekker.gamification.service;

import org.springframework.stereotype.Service;

import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;

/**
 * Class that calculates the base points for a route.
 * The base points are calculated based on the CO2 emissions and distance of the route.
 */
@Service
public class BasePoints {

    public Double car_ratio = 6D;
    public Double points = 0D;
    public Double distance = 0D;

    /**
     * Calculates the points for given CO2 emissions and distance.
     * The points are calculated using a logarithmic function that takes into account the ratio of distance to
     * CO2 emissions.
     *
     * @param co2 The CO2 emissions for the route.
     * @param distance The distance of the route.
     * @return The calculated points.
     */
    private Double calculate(Double co2, Double distance) {
        // calculate the ratio of distance to CO2 emissions
        // ratio = max(1, (distance / co2) - car_ratio)
        Double ratio = Math.max(1, 
            ((distance / co2) - car_ratio));

        // calculate the points using a logarithmic function
        // result = log10(1 + ratio) * distance
        Double result = Math.log10(1 + ratio) * distance;

        return result;
    }

    /**
     * Calculates the base points for a route.
     * The points are calculated based on the CO2 emissions and distance of the route.
     * The calculated points and distance are stored in the `points` and `distance` fields, respectively.
     *
     * @param route The route to calculate points for.
     */
    public void calculatePoints(Route route) {
        Double distance = 0D;

        // calculate the distance of the route
        for (Step step : route.getSteps()) {
            distance += step.getDistance();
        }

        // get the co2 value of the route
        Double co2 = route.getCo2();

        // calculate the points and set the distance
        this.points = calculate(co2, distance);
        this.distance = distance;
    }
}
