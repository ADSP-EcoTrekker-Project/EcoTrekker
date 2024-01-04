package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionRequest {

    @Getter
    @Setter
    private String vehicle_name;

    public VehicleConsumptionRequest(){
        super();
    }

    public VehicleConsumptionRequest(String vehicle_name) {
        this.setVehicle_name(vehicle_name);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;

        VehicleConsumptionRequest reply = (VehicleConsumptionRequest) o;

        boolean nameEquals = (this.getVehicle_name() == null && reply.getVehicle_name() == null) ||
                             (this.getVehicle_name().compareTo(reply.getVehicle_name()) == 0);
        return  nameEquals;
    }
}
