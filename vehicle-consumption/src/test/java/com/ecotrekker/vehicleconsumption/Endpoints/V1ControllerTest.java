package com.ecotrekker.vehicleconsumption.Endpoints;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionReply;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class V1ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testConsumptionEndpoint() {
        VehicleConsumptionRequest request = new VehicleConsumptionRequest("car");
        VehicleConsumptionReply reply = this.restTemplate.postForObject(
            "http://localhost:"  + port + "/v1/consumption",
            request, 
            VehicleConsumptionReply.class);
        assertEquals(0, reply.getVehicleName().compareTo(request.getVehicleName()));
    }

    @Test
    void testConsumptionEndpointWithCar() {
        VehicleConsumptionRequest request = new VehicleConsumptionRequest("car");

        ResponseEntity<VehicleConsumptionReply> response = restTemplate.postForEntity(
                "http://localhost:"  + port + "/v1/consumption",
                request,
                VehicleConsumptionReply.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        VehicleConsumptionReply reply = response.getBody();
        assertNotNull(reply);
        assertEquals("car", reply.getVehicleName());
    }

    @Test
    void testConsumptionEndpointWithInvalidVehicle() {
        VehicleConsumptionRequest request = new VehicleConsumptionRequest("invalid_vehicle");

        ResponseEntity<VehicleConsumptionReply> response = restTemplate.postForEntity(
                "http://localhost:"  + port + "/v1/consumption",
                request,
                VehicleConsumptionReply.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testConsumptionEndpointWithEmptyVehicle() {
        VehicleConsumptionRequest request = new VehicleConsumptionRequest("");

        ResponseEntity<VehicleConsumptionReply> response = restTemplate.postForEntity(
                "http://localhost:"  + port + "/v1/consumption",
                request,
                VehicleConsumptionReply.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
