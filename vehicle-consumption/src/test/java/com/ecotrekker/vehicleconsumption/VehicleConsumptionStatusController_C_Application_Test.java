package com.ecotrekker.vehicleconsumption;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VehicleConsumptionStatusController_C_Application_Test {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAliveEndpoint() throws Exception {
        assertThat(
            this.restTemplate.getForObject("http://localhost:"  + port + "/status/alive", String.class)
        ).contains("Vehicle Consumption Service is alive!");
    }

    @Test
    void testReadyEndpoint() throws Exception {
        assertThat(
            this.restTemplate.getForObject("http://localhost:"  + port + "/status/ready", String.class)
        ).contains("Vehicle Consumption Service is ready!");
    }
}
