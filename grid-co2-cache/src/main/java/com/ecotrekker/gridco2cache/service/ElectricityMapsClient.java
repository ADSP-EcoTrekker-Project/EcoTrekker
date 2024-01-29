package com.ecotrekker.gridco2cache.service;

import com.ecotrekker.gridco2cache.model.CarbonIntensityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "electricityMapsClient", url = "https://api-access.electricitymaps.com/free-tier")
public interface ElectricityMapsClient {

    @GetMapping("/carbon-intensity/latest")
    CarbonIntensityResponse getLatestCarbonIntensity(@RequestHeader("auth-token") String apiToken, @RequestParam("zone") String zone);
}
