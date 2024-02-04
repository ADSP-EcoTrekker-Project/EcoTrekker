package com.ecotrekker.co2calculator.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.co2calculator.config.FeignClientConfig;
import com.ecotrekker.co2calculator.model.CO2Response;

@FeignClient(value = "grid-co2-cache-client", url="${grid-co2-cache.address}", configuration = FeignClientConfig.class)
public interface GridCO2CacheClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "${grid-co2-cache.uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    CO2Response getCO2Intensity();
}
