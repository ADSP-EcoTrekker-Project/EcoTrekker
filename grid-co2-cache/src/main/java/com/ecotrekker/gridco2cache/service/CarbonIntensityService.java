package com.ecotrekker.gridco2cache.service;

import com.ecotrekker.gridco2cache.model.CarbonIntensityResponse;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CarbonIntensityService {

    @Value("${electricitymaps.api.key}")
    private String apiToken;

    @Getter
    private volatile Double latestCarbonIntensity;

    @Autowired
    private ElectricityMapsClient client;

    public Double getCarbonIntensityForZone(String zone) {
        CarbonIntensityResponse response = client.getLatestCarbonIntensity(apiToken, zone);
        latestCarbonIntensity = response.getCarbonIntensity();
        return latestCarbonIntensity;
    }

    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    public void updateCarbonIntensityCache() {
        CarbonIntensityResponse response = client.getLatestCarbonIntensity(apiToken, "DE");
        latestCarbonIntensity = response.getCarbonIntensity();
    }
}