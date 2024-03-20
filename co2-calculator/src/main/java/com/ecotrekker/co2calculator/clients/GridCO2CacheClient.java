package com.ecotrekker.co2calculator.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.co2calculator.model.CarbonResponse;
import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.service.GenericCacheManager;

import reactor.core.publisher.Mono;

@Component
public class GridCO2CacheClient {
    private static final String GRID_CACHE_KEY = "GRID_CACHE_KEY";
    @Value("${grid-co2-cache.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public GridCO2CacheClient(WebClient.Builder builder,  @Value("${grid-co2-cache.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }
    
    @Autowired GenericCacheManager<String, CarbonResponse> cacheManager;

    public Mono<CarbonResponse> getCO2Intensity() {
        return cacheManager.get(
            GRID_CACHE_KEY, 
            this.client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CarbonResponse.class)
                .single()
        );
    }
}