package com.ecotrekker.vehicledepot.config.depot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class VehicleDepot {
    
    @Getter
    private Map<String, TransportLine> depotRoutes;

    @Getter
    @Setter
    private Map<String, Double> vehicles;

    

    public VehicleDepot() {
        depotRoutes = new HashMap<String, TransportLine>();
    }

    public void addRoute(TransportLine route) {
        depotRoutes.put(route.getName(), route);
    }

}
