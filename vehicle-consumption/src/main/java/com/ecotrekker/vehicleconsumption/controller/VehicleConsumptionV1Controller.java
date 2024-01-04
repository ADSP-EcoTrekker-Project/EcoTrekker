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
import com.ecotrekker.vehicleconsumption.parser.implementations.ITomlVehicleConfigLoader;

@RestController
@RequestMapping(value = "/v1")
public class VehicleConsumptionV1Controller {

    @Autowired 
    IVehicleTree<ITomlVehicleConfigLoader<IVehicleTreeElement>> vehicles;

    @PostMapping("/consumption")
    public ResponseEntity<?> getVehicleConsumption(@RequestBody VehicleConsumptionRequest request){
        String name = request.getVehicleName();
        try {
            IVehicleTreeElement v = vehicles.getElementByName(name);
            VehicleConsumptionReply reply = new VehicleConsumptionReply(name, v.getKwh(), v.getCo2());
            return new ResponseEntity<VehicleConsumptionReply>(reply, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            VehicleConsumptionReply reply = new VehicleConsumptionReply(name, -1, -1);
            return new ResponseEntity<>(reply, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            VehicleConsumptionReply reply = new VehicleConsumptionReply(name, -1, -1);
            return new ResponseEntity<>(reply, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
