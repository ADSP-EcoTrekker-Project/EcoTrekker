package com.ecotrekker.co2calculator.service;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecotrekker.co2calculator.model.ConsumptionRequestBuilder;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CalculatorService {
    @Autowired
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${consumption-service.uri}")
    private String consumURI;
    
    @Value("${consumption-service.timeout}")
    private Long consumTimeout;

    public Mono<ConsumptionResponse> getVehicleConsum(RouteStep routeStep) {
        return webClient.post()
        .uri(consumURI)
        .bodyValue((new ConsumptionRequestBuilder()).setVehicle(routeStep.getVehicle()).build())
        .retrieve()
        .bodyToMono(ConsumptionResponse.class);
    }

    public String requestCalculation(Route route) throws JsonProcessingException {
        try {
            Map<String, ConsumptionResponse> consumptions = Flux.fromIterable(route.getSteps())
            .distinct(RouteStep::getVehicle)
            .flatMap(this::getVehicleConsum)
            .collectMap(ConsumptionResponse::getVehicle_name)
            .block(Duration.ofMillis(consumTimeout));

            boolean needPower = consumptions.values().stream()
            .filter(consumption -> { return consumption.getConsum_kwh_m() != null;})
            .findFirst()
            .isPresent();
            if (needPower) {
                //TODO talk to grid service
            }

            double co2 = route.getSteps()
            .stream()
            .map(routeStep -> routeStep.getDistance() * consumptions.get(routeStep.getVehicle()).getCo2_per_m())
            .reduce((a, b) -> a + b).get();

            RouteResult result = new RouteResult();
            result.setId(route.getId());
            result.setSteps(route.getSteps());
            result.setCo2(co2);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}