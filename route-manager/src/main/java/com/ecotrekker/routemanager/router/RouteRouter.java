package com.ecotrekker.routemanager.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ecotrekker.routemanager.handler.RouteHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class RouteRouter {
    
    @Bean
    public RouterFunction<ServerResponse> route(RouteHandler routeHandler) {
        return RouterFunctions
        .route(POST("/v1/calc/routes").and(accept(MediaType.APPLICATION_JSON)), routeHandler::calculateRouteData);
    }
}
