package com.ecotrekker.co2calculator.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.clients.GridCO2CacheClient;
import com.ecotrekker.co2calculator.clients.VehicleConsumptionClient;
import com.ecotrekker.co2calculator.clients.VehicleDepotClient;
import com.ecotrekker.co2calculator.model.CalculationResponse;
import com.ecotrekker.co2calculator.model.ConsumptionCache;
import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.model.RouteStepResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.ecotrekker.co2calculator.model.VehicleDepotRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalculatorService {

    @Autowired
    private VehicleConsumptionClient consumptionClient;

    @Autowired
    private VehicleDepotClient depotClient;

    @Autowired
    private GridCO2CacheClient gridClient;

    @Autowired
    private GamificationServiceModule gamificationModule;

    public Mono<CalculationResponse> requestCalculation(RouteStep step, Boolean enableGamification) {
        return Mono.just(step)
        .filter(filterStep -> filterStep.getLine() != null && filterStep.isTopLevel()) // if is empty line or not toplevel vehicle skip ahead
        .flatMap(topStep -> depotClient.getVehicleShareInDepot(new VehicleDepotRequest(topStep.getLine())))
        .onErrorResume(ex -> Mono.empty()) // incase we get error or nothing from depot service skip ahead
        .flatMap(depotReply -> 
            Flux.fromIterable(depotReply.getVehicles().entrySet())
            .flatMap(entry -> 
                consumptionClient.getCO2Intensity(new ConsumptionRequest(entry.getKey()))
                .map(consumReply -> new ConsumptionCache(entry.getValue(), consumReply.getKwh(), consumReply.getCo2()))
            )
            .collectList()
        )
        .switchIfEmpty(consumptionClient.getCO2Intensity(new ConsumptionRequest(step.getVehicle()))
            .map(consumption -> Collections.singletonList(new ConsumptionCache(1.0, consumption.getKwh(), consumption.getCo2()))
            )
        )
        .flatMap(consumptions ->
            Flux.fromIterable(consumptions)
            .filter(cache -> cache.getKwh() != null) //this may be called more than once, but it shouldnt be too bad
            .collectList()
            .filter(kwhConsums -> kwhConsums.size() >= 1) // skip ahead if we dont electric vehicles vehicle
            .flatMap(gridCo2 -> gridClient.getCO2Intensity()
                .map(gridReply -> gridReply.getCarbonIntensity()))
                .map(gridCo2 -> consumptions.stream()
                    .mapToDouble(cache -> {
                        if (cache.getKwh() != null) {
                            return cache.getKwh() * gridCo2 * cache.getShare() * step.getDistance();
                        }
                        return cache.getCo2() * cache.getShare() * step.getDistance();
                    })
                    .sum()
            )
            .switchIfEmpty(Flux.fromIterable(consumptions)
                .map(cache -> cache.getCo2() * cache.getShare() * step.getDistance())
                .reduce(0.0, (sum, result) -> sum + result)
            )
            .map(result -> {
                RouteStepResult stepResult = new RouteStepResult(step.getStart(), step.getEnd(), step.getVehicle(), step.getLine(), step.getDistance(), result);
                Double points = enableGamification ? gamificationModule.calculatePoints(stepResult) : null;
                return new CalculationResponse(stepResult, points);
            })
        );
    }
}