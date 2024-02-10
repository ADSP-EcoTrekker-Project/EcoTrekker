package com.ecotrekker.gamification.service;

import org.springframework.stereotype.Service;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;

/*
 * Contains the business logic to apply configured preferential treatment to a certain vehicle
 */
@Service
public class Preferred {
    // define factors for different transportation types
    // according to documentation, the transportation types are: suburban, subway, tram, bus, ferry, express, regional
    private static final double BIKE_FACTOR = 1.0;
    private static final double WALK_FACTOR = 1.0;
    private static final double E_SCOOTER_FACTOR = 1.0;

    private static final double SUBURBAN_FACTOR = 1.0;
    private static final double SUBWAY_FACTOR = 1.0;
    private static final double TRAM_FACTOR = 1.0;
    private static final double BUS_FACTOR = 1.0;
    private static final double FERRY_FACTOR = 1.0;
    private static final double EXPRESS_FACTOR = 1.0;
    private static final double REGIONAL_FACTOR = 1.0;

    private static final double CAR_FACTOR = 1.0;
    private static final double MOPED_FACTOR = 1.0;

    public Double applyPreferred(Route route, Double points) {
        for (Step step : route.getSteps()) {
            // apply factor based on vehicle type
            switch (step.getVehicle()) {
                case "bike":
                    points *= BIKE_FACTOR;
                    break;
                case "walking":
                    points *= WALK_FACTOR;
                    break;
                case "e_scooter":
                    points *= E_SCOOTER_FACTOR;
                    break;
                case "suburban":
                    points *= SUBURBAN_FACTOR;
                    break;
                case "subway":
                    points *= SUBWAY_FACTOR;
                    break;
                case "tram":
                    points *= TRAM_FACTOR;
                    break;
                case "bus":
                    points *= BUS_FACTOR;
                    break;
                case "ferry":
                    points *= FERRY_FACTOR;
                    break;
                case "express":
                    points *= EXPRESS_FACTOR;
                    break;
                case "regional":
                    points *= REGIONAL_FACTOR;
                    break;
                case "car":
                    points *= CAR_FACTOR;
                    break;
                case "moped":
                    points *= MOPED_FACTOR;
                    break;
                default:
                    // if vehicle type not recognized, do nothing
                    break;
            }
        }
        return points;
    }
}
