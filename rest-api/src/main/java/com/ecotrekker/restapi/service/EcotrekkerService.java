package com.ecotrekker.restapi.service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.model.RoutesResult;

@Service
public class EcotrekkerService {

    @Autowired
    private CO2CalculatorFeignClient fclient;

    @Async
    public List<CompletableFuture<RouteResult>> getCo2(List<Route> routes) {
        LinkedList<CompletableFuture<RouteResult>> results = new LinkedList<>();
        for (Route r : routes) {
            CompletableFuture<RouteResult> future = CompletableFuture.completedFuture(fclient.getRouteResult(r));
            results.add(future);
        }
        return results;
    }

    public List<RouteResult> fetchCo2(List<Route> routes) {
        List<CompletableFuture<RouteResult>> futures = getCo2(routes);
        CompletableFuture<?>[] futuresArray = futures.toArray(new CompletableFuture<?>[0]);
        CompletableFuture<List<RouteResult>> listFuture = CompletableFuture.allOf(futuresArray)
            .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        final List<RouteResult> results = listFuture.join();
        return results;
    }

    public RoutesResult requestCalculation(Routes routes) {
        try {
            List<RouteResult> routeResultList = fetchCo2(routes.getRoutes());
            RoutesResult result = new RoutesResult();
            result.setRoutes(routeResultList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}