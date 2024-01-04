package com.ecotrekker.vehicleconsumption.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTreeElement_C;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTree_C;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionReply_C;
import com.ecotrekker.vehicleconsumption.messages.VehicleConsumptionRequest_C;
import com.ecotrekker.vehicleconsumption.parser.implementations.TomlVehicleConfigLoader_C;

@RestController
@RequestMapping(value = "/v1")
public class VehicleConsumptionV1Controller_C {

    @Autowired 
    VehicleTree_C<TomlVehicleConfigLoader_C<VehicleTreeElement_C>> vehicles;

    @PostMapping("/consumption")
    public VehicleConsumptionReply_C getVehicleConsumption(@RequestBody VehicleConsumptionRequest_C request){
        String name = request.getVehicle_name();
        VehicleTreeElement_C v = vehicles.getElementByName(name);
        VehicleConsumptionReply_C reply = new VehicleConsumptionReply_C(name, v.getKwh(), v.getCo2());
        return reply;
    }
    
}
