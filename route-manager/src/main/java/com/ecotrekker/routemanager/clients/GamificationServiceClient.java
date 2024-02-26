package com.ecotrekker.routemanager.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ecotrekker.routemanager.model.GamificationReply;
import com.ecotrekker.routemanager.model.GamificationRequest;

@Component
public class GamificationServiceClient {
    @Value("${gamification-service.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public GamificationServiceClient(WebClient.Builder builder,  @Value("${gamification-service.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }

    public Mono<GamificationReply> getPoints(GamificationRequest request) {
        return this.client.post()
        .uri(uri)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(GamificationReply.class)
        .single();
    }
}
