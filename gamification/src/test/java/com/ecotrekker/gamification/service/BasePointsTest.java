package com.ecotrekker.gamification.service;

import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasePointsTest {

    private BasePoints basePoints;

    @BeforeEach
    public void setUp() {
        basePoints = new BasePoints();
    }

    @Test
    public void testCalculatePoints() {
        // create a route with a single step
        Route route = new Route();
        route.setSteps(new ArrayList<>());

        // add a step with a distance of 10 km
        Step step = new Step();
        step.setDistance(10.0);

        // add the step to the route
        route.getSteps().add(step);
        route.setCo2(20.0);

        // calculate points
        basePoints.calculatePoints(route);

        // assert the calculated points and use a delta of 0.0001 (because of double precision)
        assertEquals(3.0102, basePoints.points, 0.0001);
    }

    @Test
    public void testCalculatePointsWithMultipleSteps() {
        // create a route with multiple steps
        Route route = new Route();
        route.setSteps(new ArrayList<>());
        route.setCo2(50.0);

        // add steps with different distances
        double totalDistance = 0.0;
        for (int i = 1; i <= 5; i++) {
            // add a step with a distance of i * 10 km
            Step step = new Step();
            double distance = i * 10.0;
            step.setDistance(distance);

            // increase the total distance and add the step to the route
            totalDistance += distance;
            route.getSteps().add(step);
        }

        // calculate points
        basePoints.calculatePoints(route);

        // assert the calculated points and distance
        assertEquals(totalDistance, basePoints.distance); // total distance should be sum of individual step distances
        assertEquals(45.1545, basePoints.points, 0.0001); // points calculated based on distance and CO2 emissions
    }
}
