package com.ecotrekker.routemanager.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.ecotrekker.routemanager.model.CalculationRequest;
import com.ecotrekker.routemanager.model.CalculationResponse;
import com.ecotrekker.routemanager.model.RouteStep;

@Component
public class Co2ServiceClient {
    @Value("${co2-service.uri}")
    private String uri;

    private final WebClient client;

    @Autowired
    public Co2ServiceClient(WebClient.Builder builder,  @Value("${co2-service.address}") String address) {
        this.client = builder.baseUrl(address).build();
    }
    
    public Mono<CalculationResponse> getCo2Result(RouteStep step, Boolean enableGamification) {
        return this.client.post()
        .uri(uri)
        .bodyValue(new CalculationRequest(step, enableGamification))
        .retrieve()
        .bodyToMono(CalculationResponse.class)
        .single();
    }
}
