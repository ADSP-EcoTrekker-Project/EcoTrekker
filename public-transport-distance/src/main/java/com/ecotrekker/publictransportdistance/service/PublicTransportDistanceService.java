package com.ecotrekker.publictransportdistance.service;

import java.util.NoSuchElementException;
import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.ecotrekker.publictransportdistance.model.VehicleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Objects;

@Slf4j
@Service
public class PublicTransportDistanceService {

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

    public double calculateDistance(String start, String end, String line) {
        if (publicTransportRoutes == null || !publicTransportRoutes.containsKey(line)) {
            log.error("Received invalid parameters. start: " + start + " end: " + end + " line: " + line);
            throw new NoSuchElementException();
        }

        VehicleRoute vRoute = publicTransportRoutes.get(line);
        Optional<Double> distance = vRoute.getRoutes()
        .parallelStream()
        .map(route -> {
            AtomicBoolean isAdding = new AtomicBoolean(false);
            double totalDistance = route.getStops()
                .stream()
                .mapToDouble(stop -> {
                    if (stop.getStopName().equals(start)) {
                        isAdding.set(true);
                    }
                    if (stop.getStopName().equals(end)) {
                        isAdding.set(false);
                    }
                    if (isAdding.get()) {
                        return stop.getDistanceToNextStopKm();
                    }
                    return 0;
                })
                .sum();
            if (totalDistance == 0) {
                return null;
            }
            return totalDistance;
        })
        .filter(Objects::nonNull)
        .findAny();
        if (distance.isPresent()) {
            return distance.get();
        } else {
            log.error("Route does not exist for provided start and end stops");
            throw new IllegalArgumentException();
        }
    }
}
