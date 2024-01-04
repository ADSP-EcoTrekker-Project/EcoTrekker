package com.ecotrekker.vehicleconsumption.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public VehicleConsumptionReply getVehicleConsumption(@RequestBody VehicleConsumptionRequest request){
        String name = request.getVehicle_name();
        IVehicleTreeElement v = vehicles.getElementByName(name);
        VehicleConsumptionReply reply = new VehicleConsumptionReply(name, v.getKwh(), v.getCo2());
        return reply;
    }
    
}
