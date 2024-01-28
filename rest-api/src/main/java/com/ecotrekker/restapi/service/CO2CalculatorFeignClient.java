package com.ecotrekker.restapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.restapi.config.FeignClientConfig;
import com.ecotrekker.restapi.model.RoutesRequest;
import com.ecotrekker.restapi.model.RoutesResult;

@FeignClient(value = "route-service-client", url="${route-service.address}", configuration = FeignClientConfig.class)
public interface CO2CalculatorFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "${route-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RoutesResult getRoutesResult(@RequestBody RoutesRequest data);
}
