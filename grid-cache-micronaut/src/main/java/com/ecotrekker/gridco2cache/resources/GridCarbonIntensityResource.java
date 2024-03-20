package com.ecotrekker.gridco2cache.resources;

import com.ecotrekker.gridco2cache.messages.IntensityResponse;
import com.ecotrekker.gridco2cache.services.CarbonIntensityService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

@Controller("/v1")
public class GridCarbonIntensityResource {

    @Inject
    CarbonIntensityService service;

    @Get("/grid")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<IntensityResponse> getCarbonIntensity() {
        try {
            return HttpResponse.ok(service.getLatestCarbonIntensity());
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
        
    }
    
}
