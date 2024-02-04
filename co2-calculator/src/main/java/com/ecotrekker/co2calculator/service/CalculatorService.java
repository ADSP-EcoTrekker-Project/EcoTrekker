package com.ecotrekker.co2calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.clients.GridCO2CacheClient;
import com.ecotrekker.co2calculator.clients.VehicleConsumptionClient;
import com.ecotrekker.co2calculator.clients.VehicleDepotClient;
import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;

@Service
public class CalculatorService {
    
    @Autowired
    private VehicleConsumptionClient vehicleClient;

    @Autowired
    private VehicleDepotClient depotClient;

    @Autowired
    private GridCO2CacheClient co2Client;

    public RouteStepResult requestCalculation(RouteStep step) {
        try {
            ConsumptionResponse consumption = vehicleClient.getConsumption(new ConsumptionRequest(step.getVehicle()));
            if (consumption.getKwh() != null) {
                //TODO talk to grid service
            }

            return new RouteStepResult(step.getStart(), step.getEnd(), step.getVehicle(), step.getLine(), step.getDistance(), step.getDistance() * consumption.getCo2());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}