package com.ecotrekker.restapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;

@FeignClient(value = "co2-calculator-client", url="${co2-calculator.address}")
public interface CO2CalculatorFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/calc/co2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    RouteResult getRouteResult(@RequestBody Route data);
}
