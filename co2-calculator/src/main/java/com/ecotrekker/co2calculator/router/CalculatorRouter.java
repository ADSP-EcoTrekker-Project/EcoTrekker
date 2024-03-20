package com.ecotrekker.co2calculator.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.co2calculator.handler.Co2CalculatorHandler;
import com.ecotrekker.co2calculator.handler.StatusHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class CalculatorRouter {
    
    @Bean
    public RouterFunction<ServerResponse> routeCalc(Co2CalculatorHandler calculatorHandler) {
        return RouterFunctions
        .route(POST("/v1/calc/co2").and(accept(MediaType.APPLICATION_JSON)), calculatorHandler::calculateCo2);
    }
    
    @Bean
    public RouterFunction<ServerResponse> routeReady(StatusHandler statusHandler) {
        return RouterFunctions
        .route(GET("/status/ready"), statusHandler::getReady);
    }

    @Bean
    public RouterFunction<ServerResponse> routeAlive(StatusHandler statusHandler) {
        return RouterFunctions
        .route(GET("/status/alive"), statusHandler::getAlive);
    }
}
