package com.ecotrekker.publictransportdistance.service;

import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PublicTransportDistanceService {

    private Map<String, PublicTransportRoutes.VehicleRoute> publicTransportRoutes;

    public PublicTransportDistanceService(Resource resource) {
        ObjectMapper objectMapper = new ObjectMapper();
        PublicTransportRoutes routes = null;
        try {
            routes = objectMapper.readValue(resource.getInputStream(), PublicTransportRoutes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.publicTransportRoutes = routes.getVehicles();
    }

    public double calculateDistance(String start, String end, String vehicle) {
        if (publicTransportRoutes == null || !publicTransportRoutes.containsKey(vehicle)) {
            System.out.println("Vehicle not found");
            return -1;
        }

        PublicTransportRoutes.VehicleRoute line = publicTransportRoutes.get(vehicle);
        Optional<PublicTransportRoutes.VehicleRoute.Route> matchingRoute = line.getRoutes().stream()
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

    private boolean containsStops(PublicTransportRoutes.VehicleRoute.Route route, String start, String end) {
        List<PublicTransportRoutes.VehicleRoute.Route.Stop> stops = route.getStops();
        boolean containsStart = stops.stream().anyMatch(stop -> stop.getStopName().toLowerCase().contains(start.toLowerCase()));
        boolean containsEnd = stops.stream().anyMatch(stop -> stop.getStopName().toLowerCase().contains(end.toLowerCase()));
        return containsStart && containsEnd;
    }

    private double calculateDistanceInRoute(PublicTransportRoutes.VehicleRoute.Route route, String start, String end) {
        List<PublicTransportRoutes.VehicleRoute.Route.Stop> stops = route.getStops();
        double distance = 0.0;
        boolean started = false;

        for (PublicTransportRoutes.VehicleRoute.Route.Stop stop : stops) {
            if (stop.getStopName().toLowerCase().contains(start.toLowerCase())) {
                started = true;
            }

            if (started) {
                Double distanceToNextStopKm = stop.getDistanceToNextStopKm();
                // if null then 0, although after imputation there shouldn't be any null values anymore
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