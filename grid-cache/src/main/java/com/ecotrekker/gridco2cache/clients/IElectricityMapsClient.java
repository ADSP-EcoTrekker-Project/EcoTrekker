package com.ecotrekker.gridco2cache.clients;

import java.net.URI;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.ecotrekker.gridco2cache.messages.ElectricityMapsResponse;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Path("/free-tier/carbon-intensity/latest")
@Slf4j
public class IElectricityMapsClient {
    
    final ElectricityMapsClient client;

    public IElectricityMapsClient(@ConfigProperty(name = "electricitymaps.api.url") String baseUri) {
        log.info(baseUri.toString());
        client = QuarkusRestClientBuilder.newBuilder()
            .baseUri(URI.create(baseUri))
            .build(ElectricityMapsClient.class);
    }

    @GET
    public ElectricityMapsResponse getCarbonData(@HeaderParam("auth-token") String authToken, @QueryParam("zone") String zone) {
        return client.getCarbonData(authToken, zone);
    }

}
