package com.ecotrekker.gridco2cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;


@Service
@Slf4j
public class ElectricityMapsService {
    private static final String url = "https://api-access.electricitymaps.com/free-tier/carbon-intensity/latest?zone=DE";
    private static final String key = "yi9wa2inSOpmmrfLJGJoQRbbdtf2aF1B";


    @Autowired
    private RestTemplate restTemplate;
    public Object getGermanyCarbonIntensity(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("auth-token", key);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            log.info("Output form Electricity Maps API: {}", response.getBody());
            return response.getBody();


        } catch (Exception e){
            log.error("Something went wrong while getting data from Electricity Maps API");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception happened while calling endpoint of Electricity Maps API for getting carbon intensity",
                    e
            );

        }

    }
}
