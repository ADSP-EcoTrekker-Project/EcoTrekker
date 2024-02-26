package com.ecotrekker.routemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.routemanager.model.CalculationErrorResponse;
import com.ecotrekker.routemanager.model.RoutesRequest;
import com.ecotrekker.routemanager.service.RouteService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class RouteManagerController {
    
    @Autowired
    private RouteService routeService;

    @PostMapping("/calc/routes")
    public Mono<ServerResponse> calculateRouteData(@RequestBody RoutesRequest routeRequest) throws JsonProcessingException {
        return routeService.requestCalculation(routeRequest)
        .flatMap(result -> ServerResponse.ok().bodyValue(result))
        .onErrorResume(ex -> {
            log.error("Error handling request:", ex);
            return ServerResponse.status(400)
            .bodyValue(CalculationErrorResponse.builder().error(ex.getMessage()).build());
        });
    }

}