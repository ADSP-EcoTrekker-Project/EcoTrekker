package com.ecotrekker.gamification.controller;

import com.ecotrekker.gamification.model.GamificationRequest;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class V1ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPointsEndpoint() {
        // create multiple steps for the route
        List<Step> stepList = new ArrayList<>();

        Step step1 = new Step();
        step1.setDistance(10.0);
        step1.setVehicle("bus");
        stepList.add(step1);

        Step step2 = new Step();
        step2.setDistance(5.0);
        step2.setVehicle("tram");
        stepList.add(step2);

        Step step3 = new Step();
        step3.setDistance(7.0);
        step3.setVehicle("metro");
        stepList.add(step3);

        // create a route with the steps
        Route route = new Route();
        route.setSteps(stepList);
        route.setCo2(300.0);

        // create a GamificationRequest with the route
        GamificationRequest request = new GamificationRequest();
        request.setRoutes(new ArrayList<>());
        request.getRoutes().add(route);

        // send POST request to /v1/calc/points endpoint
        HttpEntity<GamificationRequest> requestEntity = new HttpEntity<>(request);
        ResponseEntity<GamificationRequest> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/calc/points",
                HttpMethod.POST,
                requestEntity,
                GamificationRequest.class);

        // check response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // check if response body is not null
        assertNotNull(response.getBody());

        // check if the calculated points are set for the route
        Route resultRoute = response.getBody().getRoutes().get(0);

        /*
        manually calculate the points

        ratio = max(1, (distance / co2) - car_ratio)
        basePoints = log10(ratio) * distance

        basePoints = log10(max(1,(22 / 300 - 6))) * 22 = 6.6227

        Expected points = ( basePoints * bus factor * (10 / (22 / 100))                   <- 22 is sum of all distances,
             + basePoints * tram factor * (5 / (22 / 100))                                   10 is distance of step
             + basePoints * subway factor * (7 / (22 / 100))) * rush hour factor

        assuming bus factor = 1.7, tram factor = 1.6, subway factor = 1.5
        */
        // double expectedPoints = (6.6227 * 1.7 * (10.0 / (22.0 / 100.0))
        //                 + 6.6227 * 1.6 * (5.0 / (22.0 / 100.0))
        //                 + 6.6227 * 1.5 * (7.0 / (22.0 / 100.0))) * getRushHourFactor();

        // assertNotNull(resultRoute.getPoints());
        // assertEquals(resultRoute.getPoints(), expectedPoints, 0.1);
    }

    private double getRushHourFactor() {
        // get the current time
        LocalTime currentTime = LocalTime.now();

        // define the morning and evening rush hour times
        LocalTime morningStart = LocalTime.of(7, 0);
        LocalTime morningEnd = LocalTime.of(9, 0);
        LocalTime eveningStart = LocalTime.of(17, 0);
        LocalTime eveningEnd = LocalTime.of(19, 0);

        // check if the current time is within any rush hour window
        if ((currentTime.isAfter(morningStart) && currentTime.isBefore(morningEnd)) ||
                (currentTime.isAfter(eveningStart) && currentTime.isBefore(eveningEnd))) {
            return 1.2; // rush hour factor
        }
        return 1.0; // non-rush hour factor
    }
}
