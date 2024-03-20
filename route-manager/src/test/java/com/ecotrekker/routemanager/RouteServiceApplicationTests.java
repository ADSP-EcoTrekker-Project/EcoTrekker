package com.ecotrekker.routemanager;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.ecotrekker.routemanager.model.CalculationRequest;
import com.ecotrekker.routemanager.model.CalculationResponse;
import com.ecotrekker.routemanager.model.DistanceReply;
import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.Route;
import com.ecotrekker.routemanager.model.RoutesRequest;
import com.ecotrekker.routemanager.model.RoutesResult;
import com.ecotrekker.routemanager.model.RouteStep;
import com.ecotrekker.routemanager.model.RouteStepResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
        "distance-service.address=http://localhost:8084",
        "co2-service.address=http://localhost:8083",
        "gamification-service.address=http://localhost:8082",
    }
)

public class RouteServiceApplicationTests {

    public MockWebServer mockDistanceServiceBackEnd;
    public MockWebServer mockCo2ServiceBackEnd;
    public MockWebServer mockGamificationServiceBackEnd;

    @Value("${distance-service.address}")
    private String distanceURL;
    @Value("${co2-service.address}")
    private String co2URL;
    @Value("${gamification-service.address}")
    private String gameURL;

    private ObjectMapper mapper = new ObjectMapper();

    final Dispatcher distanceDispatcher = new Dispatcher() {
        @Override
        public MockResponse dispatch (RecordedRequest request) {
            try {
                DistanceRequest requestBody = mapper.readValue(request.getBody().readUtf8(), DistanceRequest.class);
                RouteStep step = requestBody.getStep();
                return new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(mapper.writeValueAsString(new DistanceReply(step.getLine().equals("m38") ? 500.0 : 1000.0)));
            } catch (Exception e) {
                e.printStackTrace();
                return new MockResponse().setResponseCode(500);
            }
        }
    };
    final Dispatcher co2Dispatcher = new Dispatcher() {
        @Override
        public MockResponse dispatch (RecordedRequest request) {
            try {
                CalculationRequest requestBody = mapper.readValue(request.getBody().readUtf8(), CalculationRequest.class);    
                RouteStep input = requestBody.getStep();
                return new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(mapper.writeValueAsString(
                        new CalculationResponse(
                            new RouteStepResult(input.getStart(), input.getEnd(), input.getVehicle(), input.getLine(), input.getDistance(), 42.0),
                            100.0)
                        )
                    );
            } catch (Exception e) {
                e.printStackTrace();
                return new MockResponse().setResponseCode(500);
            }
        }
    };
    final Dispatcher gameDispatcher = new Dispatcher() {
        @Override
        public MockResponse dispatch (RecordedRequest request) {
            return new MockResponse().setResponseCode(501); //Gamification is not implemented yet
        }
    };

    @BeforeEach
    void setUp() throws IOException {
        mockDistanceServiceBackEnd = new MockWebServer();
        mockDistanceServiceBackEnd.setDispatcher(distanceDispatcher);
        mockDistanceServiceBackEnd.start(new URL(distanceURL).getPort());

        mockCo2ServiceBackEnd = new MockWebServer();
        mockCo2ServiceBackEnd.setDispatcher(co2Dispatcher);
        mockCo2ServiceBackEnd.start(new URL(co2URL).getPort());

        mockGamificationServiceBackEnd = new MockWebServer();
        mockGamificationServiceBackEnd.setDispatcher(gameDispatcher);
        mockGamificationServiceBackEnd.start(new URL(gameURL).getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockDistanceServiceBackEnd.shutdown();
        mockCo2ServiceBackEnd.shutdown();
        mockGamificationServiceBackEnd.shutdown();
    }
    
    @Autowired
    private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

    @Test
    void testResponse() {
        System.out.println("START TEST");
        List<RouteStep> stepsRoute1 = Stream.of(
            new RouteStep("a", "b", "bus","m36", null),
            new RouteStep("b", "c", "bus","137", null),
            new RouteStep("c", "d", "bus","m38", null))
            .collect(Collectors.toList());
        List<RouteStep> stepsRoute2 = Stream.of(
            new RouteStep("a", "b", "bus","m36", null),
            new RouteStep("b", "d", "bus","m38", null))
            .collect(Collectors.toList());
        RoutesRequest testRoutes = new RoutesRequest(
            Stream.of(
                new Route(stepsRoute1, UUID.randomUUID()),
                new Route(stepsRoute2, UUID.randomUUID()))    
            .collect(Collectors.toList()),
        false);
        
        webTestClient
        .post().uri("/v1/calc/routes")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(testRoutes)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RoutesResult.class);
    }
}
