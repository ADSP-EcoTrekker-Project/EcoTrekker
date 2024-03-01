package com.ecotrekker.co2calculator;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public class CalculatorE2ETest {

    public MockWebServer mockBackEnd;

    @Value("${consumption-service.address}")
    private String consumURL;

    private ObjectMapper mapper = new ObjectMapper();

    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch (RecordedRequest request) {

            ConsumptionResponse carResponse = new ConsumptionResponse();
            carResponse.setVehicle("/car");
            carResponse.setCo2(160D);
            ConsumptionResponse ebikeResponse = new ConsumptionResponse();
            ebikeResponse.setVehicle("/bike/e-bike");
            ebikeResponse.setCo2(21D);

            try {
                ConsumptionRequest consumptionRequest = mapper.readValue(request.getBody().readUtf8(), ConsumptionRequest.class);

                switch (consumptionRequest.getVehicle()) {
                    case "/car":
                        return new MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .setBody(mapper.writeValueAsString(carResponse));
                    case "/bike/e-bike":
                        return new MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .setBody(mapper.writeValueAsString(ebikeResponse));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new MockResponse().setResponseCode(500);
            } 

            return new MockResponse().setResponseCode(404);
        }
    };

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.setDispatcher(dispatcher);
        mockBackEnd.start(new URL(consumURL).getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
    
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testResponseCar() {
        RouteStep testRouteStep = new RouteStep("a", "b", "/car",null, 300.0);
        webTestClient.post().uri("/v1/calc/co2")
        .bodyValue(testRouteStep)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RouteStepResult.class)
        .consumeWith(body -> assertEquals(160 * 300.0, body.getResponseBody().getCo2()));
    }

    @Test
    void testResponseEbike() {
        RouteStep testRouteStep = new RouteStep("b", "c", "/bike/e-bike",null, 300.0);
        webTestClient.post().uri("/v1/calc/co2")
        .bodyValue(testRouteStep)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RouteStepResult.class)
        .consumeWith(body -> assertEquals(21.0 * 300.0, body.getResponseBody().getCo2()));
    }
}
