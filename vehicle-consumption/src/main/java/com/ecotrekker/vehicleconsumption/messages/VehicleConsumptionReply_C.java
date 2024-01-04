package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionReply_C {

    @Getter
    @Setter
    private String vehicle_name;

    @Getter
    @Setter
    private Integer consumption_kwh_m;

    @Getter
    @Setter
    private Integer co2_m;

    public VehicleConsumptionReply_C(){
        super();
    }

    public VehicleConsumptionReply_C(String vehicle_name, Integer consumption_kwh_m, Integer co2_m) {
        this.setVehicle_name(vehicle_name);
        this.setConsumption_kwh_m(consumption_kwh_m);
        this.setCo2_m(co2_m);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;

        VehicleConsumptionReply_C reply = (VehicleConsumptionReply_C) o;

        boolean nameEquals = (this.getVehicle_name() == null && reply.getVehicle_name() == null) ||
                             (this.getVehicle_name().compareTo(reply.getVehicle_name()) == 0);
        boolean consumptionEquals = (this.getConsumption_kwh_m() == reply.getConsumption_kwh_m());
        boolean co2Equals = (this.getCo2_m() == reply.getCo2_m());

        return  nameEquals && consumptionEquals && co2Equals;
    }
}
