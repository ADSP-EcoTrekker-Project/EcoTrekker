package com.ecotrekker.gridco2cache.service;

import com.ecotrekker.gridco2cache.model.CarbonIntensityResponse;
import com.ecotrekker.gridco2cache.model.CarbonResponse;

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
    private volatile CarbonResponse latestCarbonIntensity = new CarbonResponse();

    @Autowired
    private ElectricityMapsClient client;

    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    public void updateCarbonIntensityCache() {
        CarbonIntensityResponse response = client.getLatestCarbonIntensity(apiToken, "DE");
        latestCarbonIntensity.setCarbonIntensity(response.getCarbonIntensity());
    }
}