package com.ecotrekker.vehicleconsumption.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTreeElement;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTree;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionReply;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class VehicleConsumptionV1Controller {

    @Autowired
    IVehicleTree vehicles;

    @PostMapping("/consumption")
    public ResponseEntity<?> getVehicleConsumption(@RequestBody VehicleConsumptionRequest request){
        String fullname = request.getVehicleName();
        String[] name = fullname.split("\\/");
        try {
            IVehicleTreeElement v = vehicles.getElement(name);
            VehicleConsumptionReply reply = new VehicleConsumptionReply(request.getVehicleName(), v.getKwh(), v.getCo2());
            return new ResponseEntity<VehicleConsumptionReply>(reply, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            VehicleConsumptionReply reply = new VehicleConsumptionReply(request.getVehicleName(), -1D, -1D);
            return new ResponseEntity<>(reply, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            VehicleConsumptionReply reply = new VehicleConsumptionReply(request.getVehicleName(), -1D, -1D);
            return new ResponseEntity<>(reply, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
