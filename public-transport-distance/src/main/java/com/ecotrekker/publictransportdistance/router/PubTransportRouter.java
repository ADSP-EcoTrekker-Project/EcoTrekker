package com.ecotrekker.publictransportdistance.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.publictransportdistance.handler.StatusHandler;
import com.ecotrekker.publictransportdistance.handler.PubTransportHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class PubTransportRouter {
    
    @Bean
    public RouterFunction<ServerResponse> route(PubTransportHandler pubTransportHandler) {
        return RouterFunctions
        .route(POST("/v1/calc/distance").and(accept(MediaType.APPLICATION_JSON)), pubTransportHandler::calculateDistance);
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
