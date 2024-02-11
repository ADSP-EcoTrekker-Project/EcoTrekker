package com.ecotrekker.gamification.service;

import org.springframework.stereotype.Service;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that applies preferential treatment to certain vehicle types.
 * The preferential treatment is applied by adjusting the points calculated for a route based on the vehicle used in
 * each step of the route. The adjustment factors for different vehicle types are defined as constants.
 */
@Service
@Slf4j
public class Preferred {
    // define factors for different transportation types
    // according to documentation, the transportation types are: suburban, subway, tram, bus, ferry, express, regional
    private static final double BIKE_FACTOR = 1.1;
    private static final double WALK_FACTOR = 1.2;
    private static final double E_SCOOTER_FACTOR = 1.3;

    private static final double SUBURBAN_FACTOR = 1.4;
    private static final double SUBWAY_FACTOR = 1.5;
    private static final double TRAM_FACTOR = 1.6;
    private static final double BUS_FACTOR = 1.7;
    private static final double FERRY_FACTOR = 1.8;
    private static final double EXPRESS_FACTOR = 1.9;
    private static final double REGIONAL_FACTOR = 1.95;

    private static final double CAR_FACTOR = 0.8;
    private static final double MOPED_FACTOR = 0.9;

    /**
     * Applies preferential treatment to a route based on the vehicle used in each step.
     *
     * @param route The route to apply preferential treatment to.
     * @param basePoints The base points for the route.
     * @return The new total points for the route after applying preferential treatment.
     */
    public Double applyPreferred(Route route, BasePoints basePoints) {
        Double newRoutePoints = 0.0;

        // calculate the points for each step and add them to the total
        for (Step step : route.getSteps()) {
            Double stepPoints = calculateStepPoints(step, basePoints);
            newRoutePoints += stepPoints;
        }

        return newRoutePoints;
    }

    /**
     * Calculates the points for a step after, taking into account preferential treatment.
     *
     * @param step The step to calculate points for.
     * @param basePoints The base points for the route that the step is part of.
     * @return The points for the step after applying preferential treatment.
     */
    private Double calculateStepPoints(Step step, BasePoints basePoints) {
        // get the distance of the step
        Double stepDistance = step.getDistance();

        // get the factor for the vehicle type
        Double factor = getFactorForVehicle(step.getVehicle());

        // calculate the partial basePoints for the step
        Double basePointsMultiplier = stepDistance / (basePoints.distance);
        log.info("Preferred: ");
        log.info(stepDistance.toString());
        log.info(factor.toString());
        log.info(basePointsMultiplier.toString());

        // calculate the points for the step
        return basePoints.points * (factor * basePointsMultiplier);
    }

    /**
     * Gets the factor for a vehicle type to apply preferential treatment.
     *
     * @param vehicle The vehicle type.
     * @return The factor for the vehicle type.
     */
    private double getFactorForVehicle(String vehicle) {
        switch (vehicle) {
            case "e-bike":
            case "bicycle":
            case "bike":
                return BIKE_FACTOR;
            case "walking":
                return WALK_FACTOR;
            case "scooter":
                return E_SCOOTER_FACTOR;
            case "suburban":
                return SUBURBAN_FACTOR;
            case "metro":
                return SUBWAY_FACTOR;
            case "tram":
                return TRAM_FACTOR;
            case "bus":
                return BUS_FACTOR;
            case "ferry":
                return FERRY_FACTOR;
            case "express":
                return EXPRESS_FACTOR;
            case "regional":
                return REGIONAL_FACTOR;
            case "car":
                return CAR_FACTOR;
            case "moped":
                return MOPED_FACTOR;
            default:
                return 1.0;
        }
    }
}
