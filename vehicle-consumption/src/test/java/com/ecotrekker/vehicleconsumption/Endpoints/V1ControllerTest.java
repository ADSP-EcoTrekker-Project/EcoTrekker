package com.ecotrekker.vehicleconsumption.Endpoints;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionReply;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class V1ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testConsumptionEndpoint() throws Exception {
        VehicleConsumptionRequest request = new VehicleConsumptionRequest("car");
        VehicleConsumptionReply reply = this.restTemplate.postForObject(
            "http://localhost:"  + port + "/v1/consumption",
            request, 
            VehicleConsumptionReply.class);
        assertTrue(reply.getVehicleName().compareTo(request.getVehicleName()) == 0);
    }
}
