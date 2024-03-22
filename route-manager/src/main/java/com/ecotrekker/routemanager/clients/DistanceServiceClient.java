package com.ecotrekker.routemanager.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.service.GenericCacheManager;
import com.ecotrekker.routemanager.model.CalculationResponse;
import com.ecotrekker.routemanager.model.DistanceReply;

@Component
public class DistanceServiceClient {
    @Value("${distance-service.uri}")
    private String uri;
    
    private final WebClient client;

    @Autowired
    public DistanceServiceClient(WebClient.Builder builder, @Value("${distance-service.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }

    @Autowired
    private GenericCacheManager<DistanceRequest, DistanceReply> cacheManager;

    public Mono<DistanceReply> getDistance(DistanceRequest request) {
        return cacheManager.get(
            request, 
            this.client.post()
                .uri(uri)
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DistanceReply.class)
                .single()
        );
    }
}
