package com.ecotrekker.routemanager.service;

import java.util.List;
import java.util.Map;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RouteService {
    
    @Autowired
    private DistanceServiceClient distanceServiceClient;
    
    @Autowired
    private Co2ServiceClient co2ServiceClient;
    
    @Autowired
    private GamificationServiceClient gamificationServiceClient;


    private Map<RouteStep, DistanceReply> calculateDistances(RoutesRequest routeRequest) {
        return routeRequest.getRoutes()
            .stream()
            .flatMap(route -> route.getSteps().stream())
            .distinct()
            .collect(Collectors.toMap(
                step -> step,
                step -> {
                    if (step.getDistance() == null) {
                        DistanceReply reply = distanceServiceClient.getDistance(new DistanceRequest(step));
                        step.setDistance(reply.getDistance());   
                        return reply;
                    }
                    return null;
                }
                )
                );
    }
    
    private Map<RouteStep, RouteStepResult> calculateCo2(Map<RouteStep, DistanceReply> distanceFutures) {
        return distanceFutures.keySet()
        .stream()
        .collect(Collectors.toMap(
            step -> step, 
            step -> co2ServiceClient.getCo2Result(step)));
    }
    
    private List<RouteResult> calculateResults(RoutesRequest routeRequest, Map<RouteStep, RouteStepResult> co2Futures) {
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
                            log.error(step.toString());
                            return co2Futures.get(step).getCo2();
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
            Map<RouteStep, DistanceReply> distanceFutures = calculateDistances(routeRequest);
            // CompletableFuture.allOf(distanceFutures.values().toArray(new CompletableFuture[distanceFutures.size()])).get();
            Map<RouteStep, RouteStepResult> co2Futures = calculateCo2(distanceFutures);
            List<RouteResult> results = calculateResults(routeRequest, co2Futures);

            if (routeRequest.isGamification()) {
                GamificationReply gamificationReply = gamificationServiceClient.getPoints(new GamificationRequest(results));
                return new RoutesResult(gamificationReply.getRoutes(), true);
            }
    
            return new RoutesResult(results, false);
        } catch (Exception e) {
            throw new RouteServiceException("Error calculating Route data", e);
        }
    }
}