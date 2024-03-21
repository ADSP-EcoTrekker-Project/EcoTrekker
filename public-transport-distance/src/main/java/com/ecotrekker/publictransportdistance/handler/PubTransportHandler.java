package com.ecotrekker.publictransportdistance.handler;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.service.PubTransportService;

import reactor.core.publisher.Mono;

@Component
public class PubTransportHandler {
    @Autowired
    private PubTransportService distanceService;

    public Mono<ServerResponse> calculateDistance(ServerRequest request) {
        return request.bodyToMono(DistanceRequest.class)
        .flatMap(requestBody -> distanceService.calculateDistance(requestBody.getStep())
        .flatMap(result -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(new DistanceResponse(result * 1000)))
        ).onErrorResume(ex -> {
            if (ex instanceof NoSuchElementException) {
                return ServerResponse.notFound().build();
            } else if (ex instanceof IllegalArgumentException) {
                ex.printStackTrace();
                return ServerResponse.badRequest().build();
            }
            return ServerResponse.status(500).build();
        })
        );
    }

}
