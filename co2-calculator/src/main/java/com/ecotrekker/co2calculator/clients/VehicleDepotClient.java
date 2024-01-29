package com.ecotrekker.co2calculator.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.co2calculator.config.FeignClientConfig;
import com.ecotrekker.co2calculator.model.VehicleDepotMessage;

@FeignClient(value = "vehicle-depot-client", url="${depot-service.address}", configuration = FeignClientConfig.class)
public interface VehicleDepotClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "${depot-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleDepotMessage getConsumption(@RequestBody VehicleDepotMessage data);
}
