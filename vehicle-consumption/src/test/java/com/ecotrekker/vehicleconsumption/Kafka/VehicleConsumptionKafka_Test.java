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

import com.ecotrekker.vehicleconsumption.consumer.VehicleConsumptionConsumer_C;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionMessage_C;
import com.ecotrekker.vehicleconsumption.producer.VehicleConsumptionProducer_C;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
public class VehicleConsumptionKafka_Test {

    @Autowired
    private EmbeddedKafkaBroker kafkaEmbedded;

    @Value(value = "${spring.kafka.consumer-topic}")
    private String topic;

    @Autowired
    private VehicleConsumptionProducer_C producer;

    @Autowired
    private VehicleConsumptionConsumer_C consumer;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithVehicleConsumptionProducer_thenMessageReceived()
    throws Exception {
        consumer.setDebugMode(true);
        consumer.resetLatch(1);

        VehicleConsumptionMessage_C message = new VehicleConsumptionMessage_C("car", 50, 50);

        producer.sendMessage(topic, message);

        boolean messageConsumed = consumer.getCountdown().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        VehicleConsumptionMessage_C recvd_message = consumer.getLastPayload();
        assertTrue(recvd_message.equals(message));

        consumer.setDebugMode(false);
    }
}
