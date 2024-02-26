package com.ecotrekker.co2calculator.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.co2calculator.model.CalculationErrorResponse;
import com.ecotrekker.co2calculator.model.RouteStep;
import com.ecotrekker.co2calculator.service.CalculatorService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Co2CalculatorHandler {
    @Autowired
    CalculatorService calculatorService;
    
    public Mono<ServerResponse> calculateCo2(ServerRequest request) {
        return request.bodyToMono(RouteStep.class)
        .flatMap(stepRequest -> calculatorService.requestCalculation(stepRequest))
        .flatMap(result -> {
            log.info("we try to send 200 with "+ result.toString());
            return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(result));
        })
        .onErrorResume(ex -> {
            log.error("Error handling request:", ex);
                CalculationErrorResponse error = CalculationErrorResponse.builder().error("Invalid Route Data").build();
                return ServerResponse.status(400)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(error));

        });
    }

}
