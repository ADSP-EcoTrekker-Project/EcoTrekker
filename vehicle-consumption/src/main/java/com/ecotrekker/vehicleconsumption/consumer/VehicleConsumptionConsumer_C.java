package com.ecotrekker.vehicleconsumption.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class VehicleConsumptionConsumer_C {

    @Getter
    private CountDownLatch countdown = new CountDownLatch(0);

    @Getter
    @Setter
    private boolean debugMode = false;

    @Getter
    private String lastPayload;

    @KafkaListener(topics = "${spring.kafka.consumer-topic}", groupId = "${spring.kafka.consumer-group}")
    public void listenForVehicleConsumptionRequest(String message) {
        System.out.println("Moin!");
        if (debugMode) {
            lastPayload = message;
            countdown.countDown();
        }
    }

    public void resetLatch(){
        resetLatch(10);
    }

    public void resetLatch(Integer count){
        countdown = new CountDownLatch(count);
    }
}
