package com.ecotrekker.co2calculator.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.clients.GridCO2CacheClient;
import com.ecotrekker.co2calculator.clients.VehicleConsumptionClient;
import com.ecotrekker.co2calculator.clients.VehicleDepotClient;
import com.ecotrekker.co2calculator.model.CO2Response;
import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;
import com.ecotrekker.co2calculator.model.VehicleDepotResponse;
import com.ecotrekker.co2calculator.model.VehicleDepotRequest;

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
            double co2;
            if (step.getVehicle().equals("bus")) {
                //Get Vehicles
                VehicleDepotResponse depotData = depotClient.getVehicleShareInDepot(new VehicleDepotRequest(step.getLine()));
                //Get Vehicle Consumptions
                ConcurrentMap<String, CompletableFuture<ConsumptionResponse>> data = depotData.getVehicles()
                    .entrySet()
                    .parallelStream()
                    .filter(entry -> entry.getValue() == 0)
                    .collect(Collectors.toConcurrentMap(
                        entry -> entry.getKey(),
                        entry -> CompletableFuture.supplyAsync(() -> vehicleClient.getConsumption(new ConsumptionRequest(entry.getKey())))));
                List<ConsumptionResponse> consumptions = data.values().stream()
                .map(entry -> {
                    try {
                        return entry.get();
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                })
                .collect(Collectors.toList());
                //Request co2 if needed
                double gridCo2Response = 0;
                if (consumptions.stream().anyMatch(entry -> entry.getKwh() != null)) {
                    gridCo2Response = co2Client.getCO2Intensity().getCarbonIntensity();
                } 
                //Calc co2 per vehicle
                double gridCo2 = gridCo2Response; //effectively final for stream scope
                co2 = consumptions.stream().mapToDouble(entry -> {
                    String vehicle = entry.getVehicle();
                    if (entry.getKwh() != null) {
                        return depotData.getVehicles().get(vehicle) * entry.getKwh() * gridCo2 * step.getDistance();    
                    } else {
                        return depotData.getVehicles().get(vehicle) * entry.getCo2() * step.getDistance();
                    }
                }).sum();
            } else {
                ConsumptionResponse consumption = vehicleClient.getConsumption(new ConsumptionRequest(step.getVehicle()));
                if (consumption.getKwh() != null) {
                    CO2Response gridCo2 = co2Client.getCO2Intensity();
                    co2 = consumption.getKwh() * gridCo2.getCarbonIntensity() * step.getDistance();
                } else {
                    co2 = consumption.getCo2() * step.getDistance();
                }
            }
            return new RouteStepResult(step.getStart(), step.getEnd(), step.getVehicle(), step.getLine(), step.getDistance(), co2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}