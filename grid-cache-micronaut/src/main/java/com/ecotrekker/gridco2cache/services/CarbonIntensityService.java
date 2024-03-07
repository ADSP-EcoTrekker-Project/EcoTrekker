package com.ecotrekker.gridco2cache.services;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.ecotrekker.gridco2cache.clients.ElectricityMapsClient;
import com.ecotrekker.gridco2cache.messages.ElectricityMapsResponse;
import com.ecotrekker.gridco2cache.messages.IntensityResponse;

import io.micronaut.context.annotation.Property;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class CarbonIntensityService {

    @Getter
    volatile IntensityResponse latestCarbonIntensity = new IntensityResponse();

    @Inject
    ElectricityMapsClient client;

    @Property(name = "electricitymaps.api.key")
    String authToken;

    @Property(name = "electricitymaps.api.zone")
    String zone;

    @PostConstruct
    @Scheduled(cron = "0 0 * * * ?")
    void updateLatestCarbonIntensity() {
        client.getCarbonData(authToken, zone).subscribe(new Subscriber<ElectricityMapsResponse>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
             }

            @Override
            public void onNext(ElectricityMapsResponse response) {
                latestCarbonIntensity.setCarbonIntensity(response.getCarbonIntensity());
                log.info("Updated carbon intensity to " + latestCarbonIntensity.getCarbonIntensity().toString());
            }

            @Override
            public void onError(Throwable t) { }
            @Override
            public void onComplete() { }
            
        });
        
    }
    
}
