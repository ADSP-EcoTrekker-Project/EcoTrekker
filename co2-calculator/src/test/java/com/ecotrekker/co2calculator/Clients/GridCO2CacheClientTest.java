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

import com.ecotrekker.co2calculator.clients.GridCO2CacheClient;
import com.ecotrekker.co2calculator.model.CO2Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public class GridCO2CacheClientTest {
    public MockWebServer mockBackEnd;

    @Value("${grid-co2-cache.address}")
    private String targetUrl;

    private ObjectMapper mapper = new ObjectMapper();

    final Dispatcher dispatcher = new Dispatcher() {

        @Override
        public MockResponse dispatch (RecordedRequest request) {
            try {
                CO2Response response = new CO2Response();
                response.setCarbonIntensity(400);

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
    private GridCO2CacheClient gridCO2CacheClient;

    @Test
    public void testClient() {

        CO2Response response = gridCO2CacheClient.getCO2Intensity();

        assertTrue(response.getCarbonIntensity() == 400);
    }
}
