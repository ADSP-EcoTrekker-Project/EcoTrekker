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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
        "consumption-service.address=http://localhost:8082",
    }
)
public class CalculatorApplicationTests {

    public MockWebServer mockBackEnd;

    @Value("${consumption-service.address}")
    private String consumURL;

    private ObjectMapper mapper = new ObjectMapper();

    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch (RecordedRequest request) {

            ConsumptionResponse carResponse = new ConsumptionResponse();
            carResponse.setVehicle("car");
            carResponse.setCo2_per_m(160D);
            ConsumptionResponse ebikeResponse = new ConsumptionResponse();
            ebikeResponse.setVehicle("e-bike");
            ebikeResponse.setCo2_per_m(21D);

            try {
                ConsumptionRequest consumptionRequest = mapper.readValue(request.getBody().readUtf8(), ConsumptionRequest.class);

                switch (consumptionRequest.getVehicle()) {
                    case "car":
                        return new MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .setBody(mapper.writeValueAsString(carResponse));
                    case "e-bike":
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
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

	@Test
	void contextLoads() {
	}

    @Test
    void testResponse() {
        Route testRoute = new Route();
        LinkedList<RouteStep> testSteps = new LinkedList<>();
        RouteStep step1 = new RouteStep();
        step1.setDistance(300L);
        step1.setVehicle("car");
        RouteStep step2 = new RouteStep();
        step2.setDistance(300L);
        step2.setVehicle("e-bike");
        testSteps.add(step1);
        testSteps.add(step2);
        testRoute.setSteps(testSteps); 
        
        ResponseEntity<RouteResult> result = testRestTemplate.exchange(
            "http://localhost:"+port+"/v1/calc/co2", 
            HttpMethod.POST, 
            new HttpEntity<Route>(testRoute), 
            RouteResult.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(54300.0, result.getBody().getCo2());
        
    }
}
