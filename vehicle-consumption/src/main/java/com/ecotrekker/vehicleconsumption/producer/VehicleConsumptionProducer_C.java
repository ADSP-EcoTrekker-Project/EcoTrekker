package com.ecotrekker.vehicleconsumption.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionMessage_C;

@Component
public class VehicleConsumptionProducer_C {
    
    @Autowired
    private KafkaTemplate<String, VehicleConsumptionMessage_C> kafkaTemplate;

    public void sendMessage(String topicName, VehicleConsumptionMessage_C message) {
        kafkaTemplate.send(topicName, message);
    }
}
