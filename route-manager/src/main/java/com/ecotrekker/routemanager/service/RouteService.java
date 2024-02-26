package com.ecotrekker.routemanager.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.routemanager.clients.Co2ServiceClient;
import com.ecotrekker.routemanager.clients.DistanceServiceClient;
import com.ecotrekker.routemanager.clients.GamificationServiceClient;
import com.ecotrekker.routemanager.model.DistanceReply;
import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.GamificationReply;
import com.ecotrekker.routemanager.model.GamificationRequest;
import com.ecotrekker.routemanager.model.RouteServiceException;
import com.ecotrekker.routemanager.model.RouteResult;
import com.ecotrekker.routemanager.model.RouteStep;
import com.ecotrekker.routemanager.model.RouteStepResult;
import com.ecotrekker.routemanager.model.RoutesRequest;
import com.ecotrekker.routemanager.model.RoutesResult;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Service
public class RouteService {
    
    @Autowired
    private DistanceServiceClient distanceServiceClient;
    
    @Autowired
    private Co2ServiceClient co2ServiceClient;
    
    @Autowired
    private GamificationServiceClient gamificationServiceClient;

    @Value("${distance-service.address}")
    private String distanceServiceAddress;
    @Value("${distance-service.uri}")
    private String distanceServiceURI;
    
    public Mono<RoutesResult> requestCalculation(RoutesRequest routesRequest) {
        ConcurrentHashMap<RouteStep, Double> distanceCache = new ConcurrentHashMap<>();
        ConcurrentHashMap<RouteStep, RouteStepResult> co2Cache = new ConcurrentHashMap<>();
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
                    .flatMap(distance -> Mono.justOrEmpty(co2Cache.get(routeStep))
                        .switchIfEmpty(co2ServiceClient
                            .getCo2Result(new RouteStep(routeStep.getStart(), routeStep.getEnd(), routeStep.getVehicle(), routeStep.getLine(), distance))
                            .doOnNext(routeStepResult -> {
                                co2Cache.put(routeStep, routeStepResult);
                            })
                        )
                    )
                )
                .thenMany(Flux.fromIterable(route.getSteps()))
                .reduce(0.0, (sum, routeStep) -> sum + co2Cache.get(routeStep).getCo2())
                .map(totalCo2 -> new RouteResult(route.getSteps(), route.getId(), totalCo2))
            )
            .collectList()
            .doOnNext(result -> log.info("Results: "+ result.toString()))
            .map( routeResults -> {
                if (routesRequest.isGamification()) {
                    return gamificationServiceClient.getPoints(new GamificationRequest(routeResults))
                    .map(reply -> new RoutesResult(reply.getRoutes(), true))
                    .block();
                } else {
                    return new RoutesResult(routeResults, routesRequest.isGamification());
                }
            });
    }
    
}