package com.ecotrekker.routemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.routemanager.clients.Co2ServiceClient;
import com.ecotrekker.routemanager.clients.DistanceServiceClient;
import com.ecotrekker.routemanager.model.CalculationCache;
import com.ecotrekker.routemanager.model.CalculationRequest;
import com.ecotrekker.routemanager.model.CalculationResponse;
import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.RouteResult;
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
    @Autowired
    private GenericCacheManager<CalculationRequest, CalculationResponse> co2CacheManager;

    public Mono<RoutesResult> requestCalculation(RoutesRequest routesRequest) {
        return Flux.fromIterable(routesRequest.getRoutes())
            .flatMap(route -> Flux.fromIterable(route.getSteps())
                .distinct()
                .flatMap(routeStep -> Mono.justOrEmpty(routeStep.getDistance())
                    .switchIfEmpty(distanceServiceClient.getDistance(new DistanceRequest(routeStep))
                        .map(reply -> reply.getDistance())
                        .doOnNext(distance -> routeStep.setDistance(distance))
                    )
                    .flatMap(distance -> {
                        CalculationRequest request = new CalculationRequest(routeStep, routesRequest.isGamification());
                        return co2CacheManager.get(
                            request, 
                            co2ServiceClient.getCo2Result(request)
                        );
                    })
                )
                .thenMany(Flux.fromIterable(route.getSteps()))
                .reduce(new CalculationCache(0.0, 0.0), (cache, routeStep) -> {
                    CalculationRequest requestKey = new CalculationRequest(routeStep, routesRequest.isGamification());
                    CalculationResponse calculationResponseEntry = co2CacheManager.get(requestKey);
                    cache.setCo2(cache.getCo2() + calculationResponseEntry.getResult().getCo2());
                    cache.setPoints(cache.getPoints() + (calculationResponseEntry.getPoints() != null ? calculationResponseEntry.getPoints() : 0));
                    return cache;
                })  
                .map(result -> new RouteResult(route.getSteps(), route.getId(), result.getCo2(), result.getPoints()))
                
            )
            .collectList()
            .map(routeResults -> new RoutesResult(routeResults, routesRequest.isGamification()));
    }
    
}