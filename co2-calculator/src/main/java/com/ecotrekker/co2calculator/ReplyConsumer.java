package com.ecotrekker.co2calculator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;
import java.time.Duration;

public abstract class ReplyConsumer<Key,Value> {
    static final long DEFAULT_POLL_RATE_MS = 100;
    private Properties consumerProps;
    private Properties producerProps;
    private boolean running = false;
    private String requestTopic;
    private String replyTopic;
    private long pollRateMS;
    
    ReplyConsumer(Properties consumerProps, Properties producerProps, String requestTopic, String replyTopic, long pollRateMS) {
        this.consumerProps = consumerProps;
        this.producerProps = producerProps;
        this.requestTopic = requestTopic;
        this.replyTopic = replyTopic;
        this.pollRateMS = pollRateMS;
    }

    ReplyConsumer(Properties consumerProps, Properties producerProps, String requestTopic, String replyTopic) {
        this(consumerProps, producerProps,requestTopic, replyTopic, DEFAULT_POLL_RATE_MS);
    }

    abstract Value processMessage(Value message);

    public void run() throws Exception{
        if (running) {
            return;
        }
        running = true;
        try (KafkaConsumer<Key, Value> consumer = new KafkaConsumer<Key,Value>(consumerProps);
        KafkaProducer<Key, Value> replyProducer = new KafkaProducer<Key,Value>(producerProps)) {
            consumer.subscribe(Arrays.asList(requestTopic));
            while (running) {
                ConsumerRecords<Key, Value> records = consumer.poll(Duration.ofMillis(pollRateMS));
                for (ConsumerRecord<Key, Value> record : records) {
                    Value response = processMessage(record.value());
                    ProducerRecord<Key, Value> reply = new ProducerRecord<>(replyTopic, response);
                    replyProducer.send(reply);
                }
            }
        } catch(Exception e) {
            //TODO error handling
            throw e; //Rethrow
        }

    }
}
