package com.ecotrekker.gamification.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ecotrekker.gamification.model.GamificationRequest;
import com.ecotrekker.gamification.model.Route;
import com.ecotrekker.gamification.model.Step;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class V1ControllerTest {
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testPointsEndpoint() {
        Step step = new Step();
        step.setStart("Köpenick");
        step.setEnd("Pankow");
        step.setDistance(36000D);
        step.setVehicle("car");
        List<Step> stepList = new LinkedList<>();
        stepList.add(step);
        Route route = new Route();
        route.setSteps(stepList);
        route.setId(new UUID(port, port));
        route.setCo2(6000D);
        GamificationRequest request = new GamificationRequest();

        ResponseEntity<GamificationRequest> response = restTemplate.postForEntity("http://localhost:" + port + "/v1/calc/points", request, GamificationRequest.class);
        HttpStatusCode status = response.getStatusCode();
        assertTrue(status == HttpStatusCode.valueOf(HttpStatus.NOT_IMPLEMENTED.value()));        
    }

}
