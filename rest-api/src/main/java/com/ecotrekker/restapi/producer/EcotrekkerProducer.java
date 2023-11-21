package com.ecotrekker.restapi.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.Routes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;

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

    public String sendMessages(Routes routes) throws JsonProcessingException {
        for (Route route : routes.getRoutes()) {
            String routeJSON = objectMapper.writeValueAsString(route);
            kafkaTemplate.send(orderTopic, routeJSON); // We get Futures for the SendResult here
        }
        
        return "Messages sent";
    }
}