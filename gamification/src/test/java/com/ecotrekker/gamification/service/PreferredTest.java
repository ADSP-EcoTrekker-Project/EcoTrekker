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
        // create a route with a single step of biking
        Route route = new Route();

        // initialize the steps list
        route.setSteps(new ArrayList<Step>());

        // add a step with a bike and a distance of 10 km
        Step step = new Step();
        step.setVehicle("bike");
        step.setDistance(10.0);

        // add the step to the route
        route.getSteps().add(step);

        // apply preferred treatment
        Double newPoints = preferred.applyPreferred(route, basePoints);

        // Expected points = basePoints * bike factor * (10 / (50 / 100))
        // assuming bike factor = 1.1
        assertEquals(22.0, newPoints, 0.0001);
    }

    @Test
    public void testApplyPreferredWithMultipleSteps() {
        // create a route with multiple steps of different vehicles
        Route route = new Route();
        route.setSteps(new ArrayList<Step>()); // initialize the steps list

        // adding steps with different vehicles and distances
        Step bikeStep = new Step();
        bikeStep.setVehicle("bike"); // factor = 1.1
        bikeStep.setDistance(10.0);

        Step carStep = new Step();
        carStep.setVehicle("car"); // factor = 0.8
        carStep.setDistance(20.0);

        Step walkStep = new Step();
        walkStep.setVehicle("walking"); // factor = 1.2
        walkStep.setDistance(10.0);

        // add the steps to the route
        route.getSteps().add(bikeStep);
        route.getSteps().add(carStep);
        route.getSteps().add(walkStep);

        // apply preferred treatment
        Double newPoints = preferred.applyPreferred(route, basePoints);

        // Expected points = (basePoints * bike factor * (10 / (50 / 100))) +
        //                   (basePoints * car factor * (20 / (50 / 100))) +
        //                   (basePoints * walk factor * (10 / (50 / 100)))
        assertEquals(78.0, newPoints, 0.0001);
    }

    @Test
    public void testApplyPreferredWithZeroBasePoints() {
        // create a route with a single step of biking
        Route route = new Route();

        // initialize the steps list
        route.setSteps(new ArrayList<>());

        // add a step with a bike and a distance of 10 km
        Step step = new Step();
        step.setVehicle("bike");
        step.setDistance(10.0);

        // add the step to the route
        route.getSteps().add(step);

        // set base points to zero
        basePoints.setPoints(0.0);

        // apply preferred treatment
        Double newPoints = preferred.applyPreferred(route, basePoints);

        // Expected points = basePoints * bike factor * (10 / (50 / 100))
        // assuming bike factor = 1.1
        assertEquals(0.0, newPoints, 0.0001);
    }

    @Test
    public void testApplyPreferredWithZeroDistance() {
        // create a route with a single step of biking
        Route route = new Route();

        // initialize the steps list
        route.setSteps(new ArrayList<>());

        // add a step with a bike and zero distance
        Step step = new Step();
        step.setVehicle("bike");
        step.setDistance(0.0);

        // add the step to the route
        route.getSteps().add(step);

        // apply preferred treatment
        Double newPoints = preferred.applyPreferred(route, basePoints);

        // Expected points should be zero because distance is zero
        assertEquals(0.0, newPoints, 0.0001);
    }

    @Test
    public void testApplyPreferredWithUnknownVehicle() {
        // create a route with a single step of an unknown vehicle
        Route route = new Route();

        // initialize the steps list
        route.setSteps(new ArrayList<>());

        // add a step with an unknown vehicle and a distance of 10 km
        Step step = new Step();
        step.setVehicle("unknown");
        step.setDistance(10.0);

        // add the step to the route
        route.getSteps().add(step);

        // apply preferred treatment
        Double newPoints = preferred.applyPreferred(route, basePoints);

        // Expected points should be the same as base points since the vehicle is unknown
        assertEquals(20.0, newPoints, 0.0001);
    }
}
