package com.ecotrekker.restapi.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.Routes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

@Component
public class EcotrekkerProducer {
    @Value("${topic.name}")
    private String orderTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public EcotrekkerProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CompletableFuture<SendResult<String, String>>> sendMessages(Routes routes) throws JsonProcessingException {
        List<CompletableFuture<SendResult<String, String>>> futures = new LinkedList<>();
        for (Route route : routes.getRoutes()) {
            String routeJSON = objectMapper.writeValueAsString(route);
            futures.add(kafkaTemplate.send(orderTopic, routeJSON));
        }
        return futures;
    }
}