package com.ecotrekker.gridco2cache.clients;

import com.ecotrekker.gridco2cache.messages.ElectricityMapsResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/free-tier/carbon-intensity/latest")
public interface ElectricityMapsClient {

    @GET
    ElectricityMapsResponse getCarbonData(@HeaderParam("auth-token") String authToken, @QueryParam("zone") String zone);
    
}
