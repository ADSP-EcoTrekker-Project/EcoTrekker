package com.ecotrekker.co2calculator.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.co2calculator.config.FeignClientConfig;
import com.ecotrekker.co2calculator.model.ConsumptionRequest;
import com.ecotrekker.co2calculator.model.ConsumptionResponse;

@FeignClient(value = "vehicle-consumption-client", url="${consumption-service.address}", configuration = FeignClientConfig.class)
public interface VehicleConsumptionClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "${consumption-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ConsumptionResponse getConsumption(@RequestBody ConsumptionRequest data);
}
