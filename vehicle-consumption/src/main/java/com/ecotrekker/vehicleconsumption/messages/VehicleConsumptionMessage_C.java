package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionMessage_C {

    @Getter
    @Setter
    private String vehicle_name;

    @Getter
    @Setter
    private int consumption_kwh_m;

    @Getter
    @Setter
    private int co2_m;

    public VehicleConsumptionMessage_C(){
        super();
    }

    public VehicleConsumptionMessage_C(String vehicle_name, int consumption_kwh_m, int co2_m) {
        this.setVehicle_name(vehicle_name);
        this.setConsumption_kwh_m(consumption_kwh_m);
        this.setCo2_m(co2_m);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;

        VehicleConsumptionMessage_C reply = (VehicleConsumptionMessage_C) o;

        boolean nameEquals = (this.getVehicle_name() == null && reply.getVehicle_name() == null) ||
                             (this.getVehicle_name().compareTo(reply.getVehicle_name()) == 0);
        boolean consumptionEquals = (this.getConsumption_kwh_m() == reply.getConsumption_kwh_m());
        boolean co2Equals = (this.getCo2_m() == reply.getCo2_m());

        return  nameEquals && consumptionEquals && co2Equals;
    }
}
