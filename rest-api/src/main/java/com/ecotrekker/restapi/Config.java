package com.ecotrekker.restapi;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import java.util.Map;

@Configuration
public class Config {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public Config(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // get configs on application.properties/yml
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}