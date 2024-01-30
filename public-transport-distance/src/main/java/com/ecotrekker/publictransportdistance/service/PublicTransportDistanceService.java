package com.ecotrekker.publictransportdistance.service;

import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.ecotrekker.publictransportdistance.model.Route;
import com.ecotrekker.publictransportdistance.model.Stop;
import com.ecotrekker.publictransportdistance.model.VehicleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PublicTransportDistanceService {

    private Map<String, VehicleRoute> publicTransportRoutes;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${configPath}")
    private String configPath;

    @PostConstruct
    public void initialize() {
        if (configPath == null || configPath.isEmpty()) {
            throw new IllegalArgumentException("Config path is null or empty");
        }

        try {
            ClassPathResource resource = new ClassPathResource(configPath);
            InputStream inputStream = resource.getInputStream();
            File configFile = File.createTempFile("temp-config", ".json");
            FileUtils.copyInputStreamToFile(inputStream, configFile);
            PublicTransportRoutes routes = objectMapper.readValue(configFile, PublicTransportRoutes.class);
            this.publicTransportRoutes = routes != null ? routes.getVehicles() : Collections.emptyMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double calculateDistance(String start, String end, String vehicle) {
        if (publicTransportRoutes == null || !publicTransportRoutes.containsKey(vehicle)) {
            System.out.println("Vehicle not found");
            return -1;
        }

        VehicleRoute line = publicTransportRoutes.get(vehicle);
        Optional<Route> matchingRoute = line.getRoutes().stream()
                .filter(route -> containsStops(route, start, end))
                .findFirst();

        if (matchingRoute.isPresent()) {
            double distance = calculateDistanceInRoute(matchingRoute.get(), start, end);
            if (distance == -1){
                distance = calculateDistanceInRoute(matchingRoute.get(), end, start);
            }
            System.out.println("Distance between " + start + " and " + end + " for vehicle " + vehicle + ": " + distance + " meters");
            return distance;
        } else {
            System.out.println("Route does not exist for provided start and end stops");
            return -1;
        }
    }

    private boolean containsStops(Route route, String start, String end) {
        List<Stop> stops = route.getStops();
        boolean containsStart = stops.stream().anyMatch(stop -> stop.getStopName().toLowerCase().contains(start.toLowerCase()));
        boolean containsEnd = stops.stream().anyMatch(stop -> stop.getStopName().toLowerCase().contains(end.toLowerCase()));
        return containsStart && containsEnd;
    }

    private double calculateDistanceInRoute(Route route, String start, String end) {
        List<Stop> stops = route.getStops();
        double distance = 0.0;
        boolean started = false;

        for (Stop stop : stops) {
            if (stop.getStopName().toLowerCase().contains(start.toLowerCase())) {
                started = true;
            }

            if (started) {
                Double distanceToNextStopKm = stop.getDistanceToNextStopKm();
                // if null then 0, although after imputation there shouldnt be any null values anymore
                double distanceToAdd = (distanceToNextStopKm != null) ? distanceToNextStopKm * 1000 : 0; // Convert km to meters
                distance += distanceToAdd;

                if (stop.getStopName().toLowerCase().contains(end.toLowerCase())) {
                    distance -= distanceToAdd; // remove "distance to next stop" of the end station
                    return distance;
                }
            }
        }
        return -1;
    }
}