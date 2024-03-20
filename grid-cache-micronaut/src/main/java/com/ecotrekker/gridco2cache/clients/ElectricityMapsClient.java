package com.ecotrekker.gridco2cache.clients;

import com.ecotrekker.gridco2cache.messages.ElectricityMapsResponse;

import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import org.reactivestreams.Publisher;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;


@Client("${electricitymaps.api.url}")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client") 
@Header(name = ACCEPT, value = "application/json") 
@Header(name = CONTENT_TYPE, value = "application/json") 
public interface ElectricityMapsClient {

    @Get("/free-tier/carbon-intensity/latest")
    @SingleResult
    Publisher<ElectricityMapsResponse> getCarbonData(@Header(name = "auth-token") String authToken, @QueryValue("zone") String zone);
    
}
