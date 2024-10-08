package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionRequest {

    @JsonProperty(value = "vehicle", required = true)
    @JsonAlias({ "vehicle", "vehicle-name", "vehicle_name", "name", "vehicleName" })
    private String vehicleName;


    public VehicleConsumptionRequest(){
        super();
    }

    public VehicleConsumptionRequest(String vehicle_name) {
        this.setVehicleName(vehicle_name);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;

        VehicleConsumptionRequest reply = (VehicleConsumptionRequest) o;

        boolean nameEquals = (this.getVehicleName() == null && reply.getVehicleName() == null) ||
                             (this.getVehicleName().compareTo(reply.getVehicleName()) == 0);
        return  nameEquals;
    }
}
