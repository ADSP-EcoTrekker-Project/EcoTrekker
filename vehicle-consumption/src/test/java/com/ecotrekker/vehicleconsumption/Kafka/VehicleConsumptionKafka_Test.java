package com.ecotrekker.vehicleconsumption.Kafka;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.ecotrekker.vehicleconsumption.consumer.VehicleConsumptionConsumer_C;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionMessage_C;
import com.ecotrekker.vehicleconsumption.producer.VehicleConsumptionProducer_C;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers", controlledShutdown = false)
public class VehicleConsumptionKafka_Test {

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Value(value = "${spring.kafka.consumer-topic}")
    private String topic;

    @Autowired
    private VehicleConsumptionProducer_C producer;

    @Autowired
    private VehicleConsumptionConsumer_C consumer;

    private boolean sendRetryMessages(VehicleConsumptionMessage_C message, int numMessages, int numRetries, int timeout) throws Exception {
        for (int i = 0; i < numRetries; i++) {
            consumer.resetLatch(numMessages);
            for (int j = 0; j < numMessages; j++) {
                producer.sendMessage(topic, message);
            }
            boolean messageConsumed = consumer.getCountdown().await(timeout, TimeUnit.SECONDS);
            if (messageConsumed) return true;
        }
        return false;
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithVehicleConsumptionProducer_thenMessageReceived()
    throws Exception {
        consumer.setDebugMode(true);

        VehicleConsumptionMessage_C message = new VehicleConsumptionMessage_C("car", 50, 50);

        boolean messageConsumed = sendRetryMessages(message, 1, 2, 2);
        assertTrue(messageConsumed);
        messageConsumed = sendRetryMessages(message, 5, 2, 2);
        assertTrue(messageConsumed);
        VehicleConsumptionMessage_C recvd_message = consumer.getLastPayload();
        assertTrue(recvd_message.equals(message));

        consumer.setDebugMode(false);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithVehicleConsumptionProducer_withNulValues_thenMessageReceived()
    throws Exception {
        consumer.setDebugMode(true);

        VehicleConsumptionMessage_C message = new VehicleConsumptionMessage_C(null, 50, 50);

        boolean messageConsumed = sendRetryMessages(message, 1, 2, 2);
        assertTrue(messageConsumed);
        messageConsumed = sendRetryMessages(message, 5, 2, 2);
        assertTrue(messageConsumed);
        VehicleConsumptionMessage_C recvd_message = consumer.getLastPayload();
        assertTrue(recvd_message.equals(message));

        consumer.setDebugMode(false);
    }
}
