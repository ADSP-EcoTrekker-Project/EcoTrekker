package com.ecotrekker.co2calculator.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.service.GenericCacheManager;

import reactor.core.publisher.Mono;

@Component
public class VehicleConsumptionClient {
    @Value("${consumption-service.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public VehicleConsumptionClient(WebClient.Builder builder,  @Value("${consumption-service.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }

    @Autowired GenericCacheManager<ConsumptionRequest, ConsumptionResponse> cacheManager;
    
    public Mono<ConsumptionResponse> getConsumption(ConsumptionRequest request) {
        return cacheManager.get(
            request, 
            this.client.post()
                .uri(uri)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ConsumptionResponse.class)
                .single()
        );
    }
}