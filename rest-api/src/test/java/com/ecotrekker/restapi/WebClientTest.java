package com.ecotrekker.restapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.RouteStep;
import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.model.RoutesResult;
import com.ecotrekker.restapi.service.EcotrekkerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest(
    properties = {
        "co2-calculator.address=http://localhost:8080"
    }
)
public class WebClientTest {
    
    public MockWebServer mockBackEnd;
    
    @Value("${co2-calculator.address}")
    private String baseURL;

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(new URL(baseURL).getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Autowired
    private EcotrekkerService service;

    @Test
    public void getResults() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // Make Routes
        Routes routes = new Routes();
        Route route = new Route();
        RouteStep step = new RouteStep();
        step.setDistance(300L);
        step.setVehicle("car");
        LinkedList<RouteStep> stepl = new LinkedList<>();
        stepl.add(step);
        route.setSteps(stepl);
        LinkedList<Route> routel = new LinkedList<>();
        routel.add(route);
        routes.setRoutes(routel);

        // Result
        RouteResult rroute = new RouteResult();
        RouteStep rstep = new RouteStep();
        rstep.setDistance(300L);
        rstep.setVehicle("car");
        LinkedList<RouteStep> rstepl = new LinkedList<>();
        rstepl.add(rstep);
        rroute.setSteps(rstepl);
        rroute.setCo2(200D);

        mockBackEnd.enqueue(new MockResponse()
            .setBody(mapper.writeValueAsString(rroute))
            .addHeader("Content-Type", "application/json"));

        RoutesResult r = service.requestCalculation(routes);
        assertTrue(r.getRoutes().size() >= 1);
    }

}
