package com.ecotrekker.routemanager.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class RouteService {
    
    @Autowired
    private DistanceServiceClient distanceServiceClient;
    
    @Autowired
    private Co2ServiceClient co2ServiceClient;
    
    @Autowired
    private GamificationServiceClient gamificationServiceClient;


    private ConcurrentMap<RouteStep, CompletableFuture<DistanceReply>> calculateDistances(RoutesRequest routeRequest) {
        return routeRequest.getRoutes()
            .parallelStream()
            .flatMap(route -> route.getSteps().stream())
            .distinct()
            .collect(Collectors.toConcurrentMap(
                step -> step,
                step -> CompletableFuture.supplyAsync(() -> distanceServiceClient.getDistance(new DistanceRequest(step))),
                (existing, replacement) -> existing));
    }
    
    private ConcurrentMap<RouteStep, CompletableFuture<RouteStepResult>> calculateCo2(ConcurrentMap<RouteStep, CompletableFuture<DistanceReply>> distanceFutures) {
        return distanceFutures.keySet()
        .parallelStream()
        .collect(Collectors.toConcurrentMap(
            step -> step,
            step -> {
                try {
                    step.setDistance(distanceFutures.get(step).get().getDistance());  
                } catch (Exception e) {
                    throw new RuntimeException();
                }  
                return CompletableFuture.supplyAsync(() -> co2ServiceClient.getCo2Result(step));
            },
            (existing, replacement) -> existing));
    }
    
    private List<RouteResult> calculateResults(RoutesRequest routeRequest, ConcurrentMap<RouteStep, CompletableFuture<RouteStepResult>> co2Futures) {
        return routeRequest
            .getRoutes()
            .parallelStream()
            .map(route ->
                new RouteResult(
                    route.getSteps(),
                    route.getId(),
                    route
                    .getSteps()
                    .stream()
                    .mapToDouble(step -> {
                        try {
                            return co2Futures.get(step).get().getCo2();
                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    })
                    .sum()
                )
            )
            .collect(Collectors.toList());
    }
    

    public RoutesResult requestCalculation(RoutesRequest routeRequest) throws RouteServiceException {
        try {
            ConcurrentMap<RouteStep, CompletableFuture<DistanceReply>> distanceFutures = calculateDistances(routeRequest);
            ConcurrentMap<RouteStep, CompletableFuture<RouteStepResult>> co2Futures = calculateCo2(distanceFutures);
            List<RouteResult> results = calculateResults(routeRequest, co2Futures);
    
            if (routeRequest.isGamification()) {
                GamificationReply gamificationReply = gamificationServiceClient.getPoints(new GamificationRequest(results));
                //TODO Gamification Handling
            }
    
            return new RoutesResult(results);
        } catch (Exception e) {
            throw new RouteServiceException("Error calculating Route data", e);
        }
    }
}