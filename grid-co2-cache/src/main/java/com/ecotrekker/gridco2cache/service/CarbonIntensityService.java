package com.ecotrekker.gridco2cache.service;

import com.ecotrekker.gridco2cache.model.CarbonIntensityResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CarbonIntensityService {

    @Value("${electricitymaps.api.key}")
    private String apiToken;

    @Getter
    private volatile Double latestCarbonIntensity;

    private final ElectricityMapsClient client;
    private final CacheManager cacheManager;

    public CarbonIntensityService(ElectricityMapsClient client, CacheManager cacheManager) {
        this.client = client;
        this.cacheManager = cacheManager;
    }

    @Cacheable("carbonIntensity")
    public Double getCarbonIntensityForZone(String zone) {
        CarbonIntensityResponse response = client.getLatestCarbonIntensity(apiToken, zone);
        latestCarbonIntensity = response.getCarbonIntensity();
        return latestCarbonIntensity;
    }

    @Scheduled(fixedRate = 3600000) // 3600000 milliseconds = 1 hour
    public void updateCarbonIntensityCache() {
        cacheManager.getCache("carbonIntensity").clear(); // Clear the old cache
        getCarbonIntensityForZone("DE"); // Fetch new data which will be cached
    }
}