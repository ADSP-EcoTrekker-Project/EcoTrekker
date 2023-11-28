package com.ecotrekker.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TestConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    public TestConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @KafkaListener(topics = "${topic.name}", groupId = "my-cool-group")
    @SendTo("${topic.name}"+"-reply")
    public String listen(String msg) throws InterruptedException, JsonProcessingException {
        Route request = objectMapper.readValue(msg, Route.class);
        RouteResult result = new RouteResult();
        result.setId(request.getId());
        result.setSteps(request.getSteps());
        result.setCo2(999d);
        System.out.println("[Consumer] responded with "+ objectMapper.writeValueAsString(result));
        return objectMapper.writeValueAsString(result);
     }
}
