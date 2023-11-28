package com.ecotrekker.restapi.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.Routes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;

@Component
public class EcotrekkerProducer {
    @Value("${topic.name}")
    private String orderTopic;

    private final ObjectMapper objectMapper;
    private final ReplyingKafkaTemplate<String, String, String> replykafkaTemplate;

    @Autowired
    public EcotrekkerProducer(ReplyingKafkaTemplate<String, String, String> replykafkaTemplate, ObjectMapper objectMapper) {
        this.replykafkaTemplate = replykafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public HashMap<UUID, RequestReplyFuture<String, String, String>> sendMessages(Routes routes) throws JsonProcessingException {
        HashMap<UUID, RequestReplyFuture<String, String, String>> futures = new HashMap<>();
        for (Route route : routes.getRoutes()) {
            String routeJSON = objectMapper.writeValueAsString(route);
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(orderTopic, routeJSON);
            
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, String.format("%s-reply", orderTopic).getBytes()));
            futures.put(route.getId(), replykafkaTemplate.sendAndReceive(record, Duration.ofMillis(1000)));
        }
        System.out.println("send events");
        return futures;
    }
}