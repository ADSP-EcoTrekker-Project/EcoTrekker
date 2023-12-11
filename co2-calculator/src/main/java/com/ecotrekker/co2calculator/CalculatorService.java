package com.ecotrekker.co2calculator;

import java.util.Properties;

import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalculatorService extends ReplyConsumer<String, String> {
    private ObjectMapper objectMapper;

    CalculatorService(Properties consumerProps, Properties producerProps, String requestTopic, String replyTopic, ObjectMapper objectMapper, long pollRateMS) {
        super(consumerProps, producerProps, requestTopic, replyTopic, pollRateMS);
        this.objectMapper = objectMapper;
    }

    CalculatorService(Properties consumerProps, Properties producerProps, String requestTopic, String replyTopic, ObjectMapper objectMapper) {
        super(consumerProps, producerProps, requestTopic, replyTopic, DEFAULT_POLL_RATE_MS);
    }

    @Override
    String processMessage(String message) {
        try {
            Route request = objectMapper.readValue(message,  Route.class);
            RouteResult result = new RouteResult();
            result.setId(request.getId());
            result.setSteps(request.getSteps());
            result.setCo2(999d);
            return objectMapper.writeValueAsString(result);    
        } catch (Exception e) {
            // TODO: handle exception
            return e.toString();
        }
        
    }    
}
