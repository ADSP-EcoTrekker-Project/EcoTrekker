package com.ecotrekker.routemanager.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.DistanceReply;

@Component
public class DistanceServiceClient {
    @Value("${distance-service.uri}")
    private String uri;
    
    private final WebClient client;

    @Autowired
    public DistanceServiceClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8084").build();
    }

    public Mono<DistanceReply> getDistance(DistanceRequest request) {
        return this.client.post()
        .uri(uri)
        .bodyValue(request)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(DistanceReply.class)
        .single();
    }
}
