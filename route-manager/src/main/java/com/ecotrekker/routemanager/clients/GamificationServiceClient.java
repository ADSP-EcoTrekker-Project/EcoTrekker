package com.ecotrekker.routemanager.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.routemanager.config.FeignClientConfig;
import com.ecotrekker.routemanager.model.GamificationReply;
import com.ecotrekker.routemanager.model.GamificationRequest;

@FeignClient(value = "routes-gamification-client", url="${gamification-service.address}", configuration = FeignClientConfig.class)
public interface GamificationServiceClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "${gamification-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GamificationReply getPoints(@RequestBody GamificationRequest data);
}
