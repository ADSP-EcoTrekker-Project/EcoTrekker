package com.ecotrekker.restapi.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.model.RoutesResult;
import com.ecotrekker.restapi.producer.EcotrekkerProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EcotrekkerService {

    private final EcotrekkerProducer producer;
    private ObjectMapper objectMapper;

    @Autowired
    public EcotrekkerService(EcotrekkerProducer producer, ObjectMapper objectMapper) {
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public String requestCalculation(Routes routes) throws JsonProcessingException {
        HashMap<UUID, RequestReplyFuture<String, String, String>> futures = producer.sendMessages(routes);
        CompletableFuture<Void> resultFuture = CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0]));
        RoutesResult result = new RoutesResult();
        LinkedList<RouteResult> routeResults = new LinkedList<>();
        try {
            resultFuture.get(1, TimeUnit.SECONDS);
            for(RequestReplyFuture<String, String, String> requestReply: futures.values()) {
                routeResults.add(objectMapper.readValue(requestReply.get().value(), RouteResult.class));
            }
        } catch (Exception e) {
            // TODO what do we do here?
        }
        result.setRoutes(routeResults);
        return objectMapper.writeValueAsString(result);
    }
}