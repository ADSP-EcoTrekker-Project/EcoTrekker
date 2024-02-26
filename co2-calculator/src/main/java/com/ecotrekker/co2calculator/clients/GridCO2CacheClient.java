package com.ecotrekker.co2calculator.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.co2calculator.model.CO2Response;

import reactor.core.publisher.Mono;

@Component
public class GridCO2CacheClient {
    @Value("${grid-co2-cache.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public GridCO2CacheClient(WebClient.Builder builder,  @Value("${grid-co2-cache.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }
    
    public Mono<CO2Response> getCO2Intensity() {
        return this.client.get()
        .uri(uri)
        .retrieve()
        .bodyToMono(CO2Response.class)
        .single();
    }
}