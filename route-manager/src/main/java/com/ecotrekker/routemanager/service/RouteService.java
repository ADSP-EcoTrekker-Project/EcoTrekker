package com.ecotrekker.routemanager.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.routemanager.clients.Co2ServiceClient;
import com.ecotrekker.routemanager.clients.DistanceServiceClient;
import com.ecotrekker.routemanager.model.CalculationCache;
import com.ecotrekker.routemanager.model.CalculationResponse;
import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.RouteResult;
import com.ecotrekker.routemanager.model.RouteStep;
import com.ecotrekker.routemanager.model.RoutesRequest;
import com.ecotrekker.routemanager.model.RoutesResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RouteService {
    
    @Autowired
    private DistanceServiceClient distanceServiceClient;
    
    @Autowired
    private Co2ServiceClient co2ServiceClient;

    @Value("${distance-service.address}")
    private String distanceServiceAddress;
    @Value("${distance-service.uri}")
    private String distanceServiceURI;
    
    public Mono<RoutesResult> requestCalculation(RoutesRequest routesRequest) {
        ConcurrentHashMap<RouteStep, Double> distanceCache = new ConcurrentHashMap<>();
        ConcurrentHashMap<RouteStep, CalculationResponse> calcCache = new ConcurrentHashMap<>();
        return Flux.fromIterable(routesRequest.getRoutes())
            .flatMap(route -> Flux.fromIterable(route.getSteps())
                .distinct()
                .flatMap(routeStep -> Mono.justOrEmpty(routeStep.getDistance())
                    .switchIfEmpty(Mono.justOrEmpty(distanceCache.get(routeStep))
                        .switchIfEmpty(distanceServiceClient.getDistance(new DistanceRequest(routeStep))
                            .map(reply -> reply.getDistance())
                            .doOnNext(distance -> {
                                routeStep.setDistance(distance);
                                distanceCache.put(routeStep, distance);
                            })
                        )
                    )
                    .flatMap(distance -> Mono.justOrEmpty(calcCache.get(routeStep))
                        .switchIfEmpty(co2ServiceClient
                            .getCo2Result(new RouteStep(routeStep.getStart(), routeStep.getEnd(), routeStep.getVehicle(), routeStep.getLine(), distance), routesRequest.isGamification())
                            .doOnNext(routeStepResult -> {
                                calcCache.put(routeStep, routeStepResult);
                            })
                        )
                    )
                )
                .thenMany(Flux.fromIterable(route.getSteps()))
                .reduce(new CalculationCache(0.0, 0.0), (cache, routeStep) -> {
                    CalculationResponse calculationResponseEntry = calcCache.get(routeStep);
                    cache.setCo2(cache.getCo2() + calculationResponseEntry.getResult().getCo2());
                    cache.setPoints(cache.getPoints() + calculationResponseEntry.getPoints());
                    return cache;
                })  
                .map(result -> new RouteResult(route.getSteps(), route.getId(), result.getCo2(), result.getPoints()))
                
            )
            .collectList()
            .map(routeResults -> new RoutesResult(routeResults, routesRequest.isGamification()));
    }
    
}