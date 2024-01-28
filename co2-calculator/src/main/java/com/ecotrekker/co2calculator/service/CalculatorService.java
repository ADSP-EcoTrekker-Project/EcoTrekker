package com.ecotrekker.co2calculator.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;

@Service
public class CalculatorService {
    
    @Autowired
    private VehicleConsumptionFeignClient client;

    public RouteStepResult requestCalculation(RouteStep step) {
        try {
            ConsumptionResponse consumption = CompletableFuture.completedFuture(client.getConsumption(new ConsumptionRequest(step.getVehicle()))).get();
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