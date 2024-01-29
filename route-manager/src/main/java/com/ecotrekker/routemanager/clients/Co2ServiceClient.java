package com.ecotrekker.routemanager.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.routemanager.config.FeignClientConfig;
import com.ecotrekker.routemanager.model.RouteStep;
import com.ecotrekker.routemanager.model.RouteStepResult;

@FeignClient(value = "routes-co2-service-client", url="${co2-service.address}", configuration = FeignClientConfig.class)
public interface Co2ServiceClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "${co2-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RouteStepResult getCo2Result(@RequestBody RouteStep data);
}
