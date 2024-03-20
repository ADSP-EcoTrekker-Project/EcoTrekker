package com.ecotrekker.restapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.RouteStep;
import com.ecotrekker.restapi.model.RoutesRequest;
import com.ecotrekker.restapi.model.RoutesResult;
import com.ecotrekker.restapi.service.EcotrekkerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

@SpringBootTest(
    properties = {
        "route-service.address=http://localhost:8081"
    }
)
public class WebClientTest {
    
    public MockWebServer mockBackEnd;
    
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${route-service.address}")
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
        List<RouteStep> stepsRoute1 = Stream.of(
            new RouteStep("a", "b", "/bus","m36", null),
            new RouteStep("b", "c", "/bus","137", null),
            new RouteStep("c", "d", "/bus","m38", null))
            .collect(Collectors.toList());
        List<RouteStep> stepsRoute2 = Stream.of(
            new RouteStep("a", "b", "/bus","m36", null),
            new RouteStep("b", "d", "/bus","m38", null))
            .collect(Collectors.toList());
        RoutesRequest testRoutes = new RoutesRequest(
            Stream.of(
                new Route(stepsRoute1, UUID.randomUUID()),
                new Route(stepsRoute2, UUID.randomUUID()))    
            .collect(Collectors.toList()),
        false);

        RoutesResult testResponse = new RoutesResult(Stream.of(
            new RouteResult(stepsRoute1, testRoutes.getRoutes().get(0).getId(), 126.0),
            new RouteResult(stepsRoute1, testRoutes.getRoutes().get(1).getId(), 84.0)
        ).collect(Collectors.toList()), false);
        mockBackEnd.enqueue(new MockResponse()
            .setBody(mapper.writeValueAsString(testResponse))
            .addHeader("Content-Type", "application/json"));

        RoutesResult r = service.requestCalculation(testRoutes);
        assertFalse(r.getRoutes().isEmpty());
    }

    @Test
    public void getResultsEmptyRoutesRequest() throws Exception {
        RoutesRequest emptyRoutesRequest = new RoutesRequest(Collections.emptyList(), false);
        mockBackEnd.enqueue(new MockResponse()
                .setBody(mapper.writeValueAsString(new RoutesResult(Collections.emptyList(), false)))
                .addHeader("Content-Type", "application/json"));

        RoutesResult r = service.requestCalculation(emptyRoutesRequest);
        assertTrue(r.getRoutes().isEmpty());
    }

    @Test
    public void getResultsEmptyBody() {
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        RoutesResult r = service.requestCalculation(new RoutesRequest(Collections.emptyList(), false));
        assertNull(r);
    }

}
