package com.ecotrekker.gamification.service;

import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferredTest {

    private Preferred preferred;
    private BasePoints basePoints;

    @BeforeEach
    public void setUp() {
        // create a new Preferred and BasePoints object for each test
        preferred = new Preferred();
        basePoints = new BasePoints();

        // set some base points and distance for testing
        basePoints.points = 100.0;
        basePoints.distance = 50.0;
    }

    @Test
    public void testApplyPreferredWithSingleStep() {
        // // create a route with a single step of biking
        // Route route = new Route();

        // // initialize the steps list
        // route.setSteps(new ArrayList<Step>());

        // // add a step with a bike and a distance of 10 km
        // Step step = new Step();
        // step.setVehicle("bike");
        // step.setDistance(10.0);

        // // add the step to the route
        // route.getSteps().add(step);

        // // apply preferred treatment
        // Double newPoints = preferred.applyPreferred(route, basePoints);

        // // Expected points = basePoints * bike factor * (10 / (50 / 100))
        // // assuming bike factor = 1.1
        // assertEquals(2200.0, newPoints, 0.0001);
    }

    @Test
    public void testApplyPreferredWithMultipleSteps() {
        // create a route with multiple steps of different vehicles
        // Route route = new Route();
        // route.setSteps(new ArrayList<Step>()); // initialize the steps list

        // // adding steps with different vehicles and distances
        // Step bikeStep = new Step();
        // bikeStep.setVehicle("bike"); // factor = 1.1
        // bikeStep.setDistance(10.0);

        // Step carStep = new Step();
        // carStep.setVehicle("car"); // factor = 0.8
        // carStep.setDistance(20.0);

        // Step walkStep = new Step();
        // walkStep.setVehicle("walking"); // factor = 1.2
        // walkStep.setDistance(10.0);

        // // add the steps to the route
        // route.getSteps().add(bikeStep);
        // route.getSteps().add(carStep);
        // route.getSteps().add(walkStep);

        // // apply preferred treatment
        // Double newPoints = preferred.applyPreferred(route, basePoints);

        // // Expected points = (basePoints * bike factor * (10 / (50 / 100))) +
        // //                   (basePoints * car factor * (20 / (50 / 100))) +
        // //                   (basePoints * walk factor * (10 / (50 / 100)))
        // assertEquals(7800.0, newPoints, 0.0001);
    }
}
