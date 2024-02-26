package com.ecotrekker.routemanager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.routemanager.model.CalculationErrorResponse;
import com.ecotrekker.routemanager.model.RoutesRequest;
import com.ecotrekker.routemanager.service.RouteService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RouteHandler {
    @Autowired
    private RouteService routeService;

    public Mono<ServerResponse> calculateRouteData(ServerRequest request) {
        return request.bodyToMono(RoutesRequest.class)
        .flatMap(routeRequest -> routeService.requestCalculation(routeRequest))
        .flatMap(result -> {
            log.info("we try to send 200 with "+ result.toString());
            return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(result));
        })
        .onErrorResume(ex -> {
            log.error("Error handling request:", ex);
            return ServerResponse.status(400)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(CalculationErrorResponse.builder().error(ex.getMessage()).build()));
        });
    }

}
