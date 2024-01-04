package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionReply {

    @Getter
    @Setter
    private String vehicleName;

    @Getter
    @Setter
    private Integer consumptionKwhM;

    @Getter
    @Setter
    private Integer co2M;

    public VehicleConsumptionReply(){
        super();
    }

    public VehicleConsumptionReply(String vehicle_name, Integer consumption_kwh_m, Integer co2_m) {
        this.setVehicleName(vehicle_name);
        this.setConsumptionKwhM(consumption_kwh_m);
        this.setCo2M(co2_m);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (o == null) return false;

        if (this.getClass() != o.getClass()) return false;

        VehicleConsumptionReply reply = (VehicleConsumptionReply) o;

        boolean nameEquals = (this.getVehicleName() == null && reply.getVehicleName() == null) ||
                             (this.getVehicleName().compareTo(reply.getVehicleName()) == 0);
        boolean consumptionEquals = (this.getConsumptionKwhM() == reply.getConsumptionKwhM());
        boolean co2Equals = (this.getCo2M() == reply.getCo2M());

        return  nameEquals && consumptionEquals && co2Equals;
    }
}
