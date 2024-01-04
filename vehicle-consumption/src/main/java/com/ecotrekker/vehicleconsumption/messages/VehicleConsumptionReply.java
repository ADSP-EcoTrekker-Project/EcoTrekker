package com.ecotrekker.vehicleconsumption.messages;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class VehicleConsumptionReply {

    @Setter
    @JsonProperty(required = true)
    @JsonAlias({ "vehicle", "vehicle-name", "vehicle_name", "name", "vehicleName" })
    private String vehicleName;

    @JsonProperty("vehicle")
    public String getVehicleName() {
        return vehicleName;
    }

    @Setter
    @JsonAlias({ "kwh", "consumption", "consum_kwh_m", "consumptionKwhM" })
    private Double consumptionKwhM;

    @JsonProperty("consum_kwh_m")
    public Double getConsumptionKwhM() {
        return consumptionKwhM;
    }

    @Setter
    @JsonAlias({ "emissions", "co2", "co2_per_m", "co2M" })
    private Double co2M;

    @JsonProperty("co2_per_m")
    public Double getCo2M() {
        return co2M;
    }

    public VehicleConsumptionReply(){
        super();
    }

    public VehicleConsumptionReply(String vehicle_name, Double consumption_kwh_m, Double co2_m) {
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
