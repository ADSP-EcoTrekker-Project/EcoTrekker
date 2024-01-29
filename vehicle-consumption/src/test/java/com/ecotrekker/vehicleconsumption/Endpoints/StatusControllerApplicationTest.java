package com.ecotrekker.vehicleconsumption.Endpoints;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatusControllerApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAliveEndpoint() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:"  + port + "/status/alive", String.class);
        HttpStatusCode status = response.getStatusCode();
        assertTrue(status == HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @Test
    void testReadyEndpoint() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:"  + port + "/status/ready", String.class);
        HttpStatusCode status = response.getStatusCode();
        assertTrue(status == HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
