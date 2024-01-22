package com.ecotrekker.vehicledepot.controller;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.ecotrekker.vehicledepot.messages.DepotMessage;

@RestController
@RequestMapping(value = "/v1")
public class V1Controller {

    @Autowired
    private Map<String, TransportLine> lineMap;

    @GetMapping(value = "/line")
    public ResponseEntity<?> getLineElectricalShare(@RequestBody DepotMessage request) {
        try {
            TransportLine line = lineMap.get(request.getLine());
            if (line == null) {
                throw new NoSuchElementException("Could not find line!");
            }
            VehicleDepot depot = line.getDepot();
            request.setShareElectrical(depot.getShareElectrical());
            return new ResponseEntity<DepotMessage>(request, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
}
