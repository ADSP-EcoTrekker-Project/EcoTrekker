package com.ecotrekker.publictransportdistance.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ecotrekker.publictransportdistance.model.Pair;
import com.ecotrekker.publictransportdistance.model.Route;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import com.ecotrekker.publictransportdistance.model.Stop;
import com.ecotrekker.publictransportdistance.model.VehicleRoute;

@Service
public class PubTransportService {
    private Map<String, VehicleRoute> publicTransportRoutes;
    private Map<String, Map<UUID,Map<String, List<Integer>>>> routeStepIndices;

    private void generateCache() {
        routeStepIndices = publicTransportRoutes.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    vehicleRouteEntry -> vehicleRouteEntry.getValue().getRoutes().stream()
                        .collect(
                            Collectors.toMap(
                                Route::getId,
                                route -> {
                                    List<Stop> stops = route.getStops();
                                    return IntStream.range(0, stops.size())
                                        .boxed()
                                        .collect(
                                            Collectors.groupingBy(i -> stops.get(i).getStopName())
                                        );
                                }
                            )
                        )
                )
            );
    }

    public void loadMapData(Map<String, VehicleRoute> publicTransportRoutes) {
        this.publicTransportRoutes = publicTransportRoutes;
        generateCache();
    }

    private Pair<Integer,Integer> findClosestIndexPair(List<Integer> startIndices, List<Integer> stopIndices) {
        int i = 0, j = 0;
        int minDiff = Integer.MAX_VALUE;
        Pair<Integer,Integer> result = new Pair<>(0, 0);
        
        while (i < startIndices.size() && j < stopIndices.size()) {
            int diff = Math.abs(startIndices.get(i) - stopIndices.get(j));
            if (diff < minDiff) {
                minDiff = diff;
                result.setFirst(startIndices.get(i));
                result.setSecond(stopIndices.get(j));
            }
            if (startIndices.get(i) < stopIndices.get(j)) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    public Mono<Double> calculateDistance(RouteStep step) {
        if (publicTransportRoutes == null || !publicTransportRoutes.containsKey(step.getLine())) {
            return Mono.error(new NoSuchElementException());
        }
        Flux<Double> distances = Flux.fromIterable(publicTransportRoutes.get(step.getLine()).getRoutes())
            .flatMap(route -> {
                //if the cache doesnt contain the route, then our data got corrupted somehow, as the cache is calculated on startup
                List<Integer> startIndices = routeStepIndices.get(step.getLine()).get(route.getId()).get(step.getStart());
                List<Integer> stopIndices = routeStepIndices.get(step.getLine()).get(route.getId()).get(step.getEnd());
                if (startIndices == null || startIndices.isEmpty() || stopIndices == null || stopIndices.isEmpty()) {
                    return Mono.empty();
                }
                Pair<Integer,Integer> indexPair;
                if (startIndices.size() > 1 || stopIndices.size() > 1) {
                    //indices are sorted due to the way the cache is generated
                    indexPair = findClosestIndexPair(startIndices, stopIndices);
                } else {
                    indexPair = new Pair<>(startIndices.get(0), stopIndices.get(0));
                }
                boolean reversed = indexPair.getFirst() > indexPair.getSecond();
                double distance = route.getStops()
                    .subList(!reversed ? indexPair.getFirst() : indexPair.getSecond(), !reversed ? indexPair.getSecond() : indexPair.getFirst()).stream()
                    .mapToDouble(stop -> stop.getDistanceToNextStopKm())
                    .sum();
                if (reversed) {
                    return Mono.just(-distance);
                } else {
                    return Mono.just(distance);
                }
            });

        Flux<Double> positiveDistances = distances.filter(distance -> distance > 0);
        Flux<Double> negativeDistances = distances.filter(distance -> distance < 0).map(distance -> distance * -1);

        return Flux.concat(positiveDistances, negativeDistances)
            .next()
            .switchIfEmpty(Mono.error(new NoSuchElementException()));
    }
}
