package com.ecotrekker.vehicleconsumption.Endpoints;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.ecotrekker.vehicleconsumption.controller.VehicleConsumptionStatusController;

@SpringBootTest
public class VehicleConsumptionStatusController_C_Controller_Test {

    @Autowired
    private VehicleConsumptionStatusController controller;
    
    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
    
}
