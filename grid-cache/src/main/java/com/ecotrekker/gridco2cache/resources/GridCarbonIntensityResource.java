package com.ecotrekker.gridco2cache.resources;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import com.ecotrekker.gridco2cache.messages.IntensityResponse;
import com.ecotrekker.gridco2cache.services.CarbonIntensityService;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Path("/v1")
public class GridCarbonIntensityResource {

    @Inject
    CarbonIntensityService service;

    @GET
    @Path("/grid")
    @Produces()
    public RestResponse<IntensityResponse> getCarbonIntensity() {
        return ResponseBuilder.ok(service.getLatestCarbonIntensity(), MediaType.APPLICATION_JSON)
            .build();
    }
    
}
