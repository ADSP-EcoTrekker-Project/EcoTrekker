package com.ecotrekker.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecotrekker.restapi.model.RoutesRequest;
import com.ecotrekker.restapi.model.RoutesResult;

@Service
public class EcotrekkerService {

    @Autowired
    private CO2CalculatorFeignClient fclient;

    public RoutesResult requestCalculation(RoutesRequest routes) {
        try {
            return fclient.getRoutesResult(routes);
        } catch (Exception e) {
            return null;
        }
    }
}