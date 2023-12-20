package com.ecotrekker.restapi.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.model.RoutesResult;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EcotrekkerService {

    @Autowired
    private WebClient client;

    @Value("${co2-calculator.timeout}")
    private int timeout;

    public Mono<RouteResult> getCo2(Route route){
        UriSpec<RequestBodySpec> uriSpec = client.post();
        RequestBodySpec bodySpec = uriSpec.uri("/v1/calc/co2");
        RequestHeadersSpec<?> headersSpec = bodySpec.body(
            Mono.just(route), Route.class);
        ResponseSpec responseSpec = headersSpec.header(
            HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
            .retrieve();
        Mono<RouteResult> response = responseSpec.bodyToMono(RouteResult.class);
        return response;
    }

    public Flux<RouteResult> fetchCO2(List<Route> routes){
        return Flux.fromIterable(routes).flatMap(this::getCo2);
    }

    public RoutesResult requestCalculation(Routes routes) {
        try {
            Flux<RouteResult> fetchResults = fetchCO2(routes.getRoutes());
            List<RouteResult> routeResultList = fetchResults.collectList().block(Duration.ofMillis(timeout));
            RoutesResult result = new RoutesResult();
            result.setRoutes(routeResultList);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}