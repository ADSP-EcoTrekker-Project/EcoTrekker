package com.ecotrekker.co2calculator.Clients;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.ecotrekker.co2calculator.clients.VehicleDepotClient;
import com.ecotrekker.co2calculator.model.VehicleDepotMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public class VehicleDepotClientTest {
    public MockWebServer mockBackEnd;

    @Value("${depot-service.address}")
    private String targetUrl;

    private ObjectMapper mapper = new ObjectMapper();

    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch (RecordedRequest request) {
            try {
                VehicleDepotMessage requestBody = mapper.readValue(request.getBody().readUtf8(), VehicleDepotMessage.class);

                VehicleDepotMessage response = new VehicleDepotMessage();
                Map<String, Double> responseMap = new HashMap<>();
                responseMap.put("e-bus", 0.5);
                responseMap.put("bus", 0.5);
                response.setLine(requestBody.getLine());
                response.setVehicles(responseMap);

                return new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody(mapper.writeValueAsString(response));
            } catch (Exception e) {
                e.printStackTrace();
                return new MockResponse().setResponseCode(500);
            }            
        }
    };

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.setDispatcher(dispatcher);
        mockBackEnd.start(new URL(targetUrl).getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Autowired
    private VehicleDepotClient vehicleDepotClient;

    @LocalServerPort
    private Integer port;

    @Test
    public void testClient() {
        VehicleDepotMessage request = new VehicleDepotMessage();
        request.setLine("169");

        VehicleDepotMessage response = vehicleDepotClient.getConsumption(request);

        assertTrue(response.getLine().equals(response.getLine()));

        assertTrue(response.getVehicles().size() == 2);
    }
}