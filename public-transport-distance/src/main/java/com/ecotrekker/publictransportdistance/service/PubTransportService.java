package com.ecotrekker.publictransportdistance.service;

import java.util.NoSuchElementException;

import com.ecotrekker.publictransportdistance.model.RouteStep;
import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.ecotrekker.publictransportdistance.model.VehicleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class PubTransportService {

    private Map<String, VehicleRoute> publicTransportRoutes;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${configPath}")
    private String configPath;

    @PostConstruct
    public void initialize() {
        log.info("Loading routes from "+Paths.get(configPath).toAbsolutePath());
        if (configPath == null || configPath.isEmpty()) {
            throw new IllegalArgumentException("Config path is null or empty");
        }

        try {
            Path configPath = Paths.get(this.configPath).toAbsolutePath();
            File configFile = configPath.toFile();
            PublicTransportRoutes routes = objectMapper.readValue(configFile, PublicTransportRoutes.class);
            this.publicTransportRoutes = routes != null ? routes.getVehicles() : Collections.emptyMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mono<Double> calculateDistance(RouteStep step) {
        if (publicTransportRoutes == null || !publicTransportRoutes.containsKey(step.getLine())) {
            return Mono.error(new NoSuchElementException());
        }
        Flux<Double> distances = Flux.fromIterable(publicTransportRoutes.get(step.getLine()).getRoutes())
            .flatMap(route -> {
                AtomicBoolean isAdding = new AtomicBoolean(false);
                AtomicBoolean isReversed = new AtomicBoolean(false);
                double totalDistance = route.getStops()
                    .stream()
                    .mapToDouble(stop -> {
                        if (stop.getStopName().equals(step.getStart())) {
                            isAdding.set(true);
                        }
                        if (stop.getStopName().equals(step.getEnd())) {
                            boolean foundStart = isAdding.get();
                            if (!foundStart) {
                                isReversed.set(true);
                            }
                            isAdding.set(!foundStart);
                        }
                        if (isAdding.get()) {
                            return stop.getDistanceToNextStopKm();
                        }
                        return 0;
                    })
                    .sum();
                if (totalDistance == 0) {
                    return Mono.empty();
                }
                if (isReversed.get()) {
                    return Mono.just(-totalDistance);
                }
                return Mono.just(totalDistance);
            }
        );

        Flux<Double> positiveDistances = distances.filter(distance -> distance > 0);
        Flux<Double> negativeDistances = distances.filter(distance -> distance < 0).map(distance -> distance * -1);

        return Flux.concat(positiveDistances, negativeDistances)
            .next()
            .switchIfEmpty(Mono.error(new NoSuchElementException()));
    }
}
