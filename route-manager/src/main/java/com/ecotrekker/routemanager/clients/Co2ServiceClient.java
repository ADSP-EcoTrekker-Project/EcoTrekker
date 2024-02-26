package com.ecotrekker.routemanager.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ecotrekker.routemanager.model.RouteStep;
import com.ecotrekker.routemanager.model.RouteStepResult;

@Component
public class Co2ServiceClient {
    @Value("${co2-service.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public Co2ServiceClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8083").build();
    }
    
    public Mono<RouteStepResult> getCo2Result(RouteStep step) {
        return this.client.post()
        .uri(uri)
        .bodyValue(step)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(RouteStepResult.class);
    }
}
