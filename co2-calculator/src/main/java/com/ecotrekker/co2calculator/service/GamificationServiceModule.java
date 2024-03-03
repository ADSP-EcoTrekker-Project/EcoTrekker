package com.ecotrekker.co2calculator.service;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecotrekker.co2calculator.model.RouteStepResult;
import com.ecotrekker.co2calculator.model.TimeWindow;

@Service
public class GamificationServiceModule {
    
    private static final Double CAR_RATIO = 10D;
    private static final HashMap<String, Double> PREF_RATIO = new HashMap<String,Double>(
        Map.ofEntries(
            Map.entry("e-bike", 1.1),
            Map.entry("bicycle", 1.1),
            Map.entry("bike", 1.1),
            Map.entry("walking", 1.2),
            Map.entry("scooter", 1.3),
            Map.entry("suburban", 1.4),
            Map.entry("metro", 1.5),
            Map.entry("tram", 1.6),
            Map.entry("bus", 1.7),
            Map.entry("express", 1.8),
            Map.entry("express", 1.9),
            Map.entry("regional", 1.95),
            Map.entry("car", 0.8),
            Map.entry("moped", 0.9)
        )
    );
    private static final Double DEF_PREF_MULTIPLIER = 1.0;
    private static final List<TimeWindow> RUSH_HOUR_WINDOWS = Arrays.asList(
        new TimeWindow(LocalTime.of(7, 0), LocalTime.of(9, 0), 1.2),
        new TimeWindow(LocalTime.of(17, 0), LocalTime.of(19, 0), 1.2)
    );
    private static final Clock clock = Clock.systemDefaultZone();
    private static final Double NON_RUSH_HOUR_FACTOR = 1.0;

    private Double calcBasePoints(RouteStepResult routeStepResult) {
        Double ratio = Math.max(
            1D, 
            ((routeStepResult.getDistance() / routeStepResult.getCo2()) - CAR_RATIO)
        );
        return Math.log10(ratio) * routeStepResult.getDistance();
    }

    private double prefVehicleMultiplier(String vehicle) {
        String topLevelElement = vehicle.split("/", 2)[0];
        Optional<Double> factor = Optional.ofNullable(PREF_RATIO.get(topLevelElement));
        return factor.orElse(DEF_PREF_MULTIPLIER);
    }

    private double rushHourMultiplier() {
        LocalTime currentTime = LocalTime.now(clock);
        for (TimeWindow window : RUSH_HOUR_WINDOWS) {
            if (window.isInTimeWindow(currentTime)) {
                return window.getFactor();
            }
        }
        return NON_RUSH_HOUR_FACTOR;
    }

    public Double calculatePoints(RouteStepResult routeStepResult) {
        return calcBasePoints(routeStepResult) * prefVehicleMultiplier(routeStepResult.getVehicle()) * rushHourMultiplier();
    }
}
