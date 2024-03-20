package com.ecotrekker.co2calculator.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class StatusHandler {
    public Mono<ServerResponse> getAlive(ServerRequest request) {
        return ServerResponse.ok().build();
    }
    public Mono<ServerResponse> getReady(ServerRequest request) {
        return ServerResponse.ok().build();
    }
}
