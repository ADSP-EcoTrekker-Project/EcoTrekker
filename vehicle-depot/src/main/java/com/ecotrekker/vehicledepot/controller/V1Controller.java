package com.ecotrekker.vehicledepot.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.ecotrekker.vehicledepot.messages.DepotMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class V1Controller {

    @Autowired
    private Map<String, TransportLine> lineMap;

    @PostMapping(value = "/depot")
    public ResponseEntity<?> getLineElectricalShare(@RequestBody DepotMessage request) {
        log.info(request.toString());
        try {
            TransportLine line = lineMap.get(request.getLine());
            VehicleDepot depot = line.getDepot();
            request.setVehicles(depot.getVehicles());
            return new ResponseEntity<DepotMessage>(request, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
}
