package com.ecotrekker.gridco2cache.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ecotrekker.gridco2cache.clients.IElectricityMapsClient;
import com.ecotrekker.gridco2cache.messages.ElectricityMapsResponse;
import com.ecotrekker.gridco2cache.messages.IntensityResponse;

import io.quarkus.scheduler.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class CarbonIntensityService {

    @Getter
    volatile IntensityResponse latestCarbonIntensity = new IntensityResponse();

    @Inject
    IElectricityMapsClient client;

    @ConfigProperty(name = "electricitymaps.api.key")
    String authToken;

    @ConfigProperty(name = "electricitymaps.api.zone")
    String zone;

    @PostConstruct
    @Scheduled(cron = "0 0 * * * ?")
    void updateLatestCarbonIntensity() {
        ElectricityMapsResponse response = client.getCarbonData(null, null);
        latestCarbonIntensity.setCarbonIntensity(response.getCarbonIntensity());
        log.info("Updated carbon intensity to " + latestCarbonIntensity.getCarbonIntensity().toString());
    }
    
}
