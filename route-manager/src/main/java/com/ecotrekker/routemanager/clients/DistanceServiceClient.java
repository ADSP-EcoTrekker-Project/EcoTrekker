package com.ecotrekker.routemanager.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecotrekker.routemanager.config.FeignClientConfig;
import com.ecotrekker.routemanager.model.DistanceRequest;
import com.ecotrekker.routemanager.model.DistanceReply;

@FeignClient(value = "routes-distance-client", url="${distance-service.address}", configuration = FeignClientConfig.class)
public interface DistanceServiceClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "${distance-service.uri}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    DistanceReply getDistance(@RequestBody DistanceRequest data);
}
