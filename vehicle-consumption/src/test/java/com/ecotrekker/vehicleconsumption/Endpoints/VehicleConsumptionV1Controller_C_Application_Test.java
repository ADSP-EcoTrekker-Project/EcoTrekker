package com.ecotrekker.vehicleconsumption.Endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionReply_C;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionRequest_C;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VehicleConsumptionV1Controller_C_Application_Test {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testConsumptionEndpoint() throws Exception {
        VehicleConsumptionRequest_C request = new VehicleConsumptionRequest_C("car");
        VehicleConsumptionReply_C reply = this.restTemplate.postForObject(
            "http://localhost:"  + port + "/v1/consumption",
            request, 
            VehicleConsumptionReply_C.class);
        assertTrue(reply.getVehicle_name().compareTo(request.getVehicle_name()) == 0);
    }
}
