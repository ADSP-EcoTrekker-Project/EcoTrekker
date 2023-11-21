package com.ecotrekker.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecotrekker.restapi.model.Routes;
import com.ecotrekker.restapi.producer.EcotrekkerProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EcotrekkerService {

    private final EcotrekkerProducer producer;

    @Autowired
    public EcotrekkerService(EcotrekkerProducer producer) {
        this.producer = producer;
    }

    public String requestCalculation(Routes routes) throws JsonProcessingException {
        return producer.sendMessages(routes);
    }
}