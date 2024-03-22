package com.ecotrekker.co2calculator.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.co2calculator.model.VehicleDepotRequest;
import com.ecotrekker.co2calculator.model.VehicleDepotResponse;
import com.ecotrekker.co2calculator.service.GenericCacheManager;

import reactor.core.publisher.Mono;

@Component
public class VehicleDepotClient {
    @Value("${depot-service.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public VehicleDepotClient(WebClient.Builder builder,  @Value("${depot-service.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }

    @Autowired GenericCacheManager<VehicleDepotRequest, VehicleDepotResponse> cacheManager;
    
    public Mono<VehicleDepotResponse> getVehicleShareInDepot(VehicleDepotRequest request) {
        return cacheManager.get(
            request, 
            this.client.post()
                .uri(uri)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(VehicleDepotResponse.class)
                .single()
        );
    }
}