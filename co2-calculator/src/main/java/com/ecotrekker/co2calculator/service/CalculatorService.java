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
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.model.RouteStep;

@Service
public class CalculatorService {
    
    @Autowired
    private VehicleConsumptionFeignClient client;

    @Async
    public List<CompletableFuture<ConsumptionResponse>> getVehicleConsumption(List<RouteStep> steps) {
        LinkedList<CompletableFuture<ConsumptionResponse>> results = new LinkedList<>();
        for (RouteStep step : steps) {
            ConsumptionRequest request = new ConsumptionRequest();
            request.setVehicle(step.getVehicle());
            CompletableFuture<ConsumptionResponse> future = CompletableFuture.completedFuture(client.getConsumption(request));
            results.add(future);
        }
        return results;
    }

    public List<ConsumptionResponse> fetchVehicleConsumption(List<RouteStep> steps) {
        List<CompletableFuture<ConsumptionResponse>> futures = getVehicleConsumption(steps);
        CompletableFuture<?>[] futuresArray = futures.toArray(new CompletableFuture<?>[0]);
        CompletableFuture<List<ConsumptionResponse>> listFuture = CompletableFuture.allOf(futuresArray)
            .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        final List<ConsumptionResponse> results = listFuture.join();
        return results;
    }
    

    public RouteResult requestCalculation(Route route) {
        try {
            Map<String, ConsumptionResponse> consumptions = fetchVehicleConsumption(route.getSteps())
                .stream()
                .collect(Collectors.toMap(ConsumptionResponse::getVehicle, Function.identity()));

            boolean needPower = consumptions.values().stream()
            .filter(consumption -> { return consumption.getKwh() != null;})
            .findFirst()
            .isPresent();
            if (needPower) {
                //TODO talk to grid service
            }

            double co2 = route.getSteps()
            .stream()
            .map(routeStep -> routeStep.getDistance() * consumptions.get(routeStep.getVehicle()).getCo2())
            .reduce((a, b) -> a + b).get();

            RouteResult result = new RouteResult();
            result.setId(route.getId());
            result.setSteps(route.getSteps());
            result.setCo2(co2);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}