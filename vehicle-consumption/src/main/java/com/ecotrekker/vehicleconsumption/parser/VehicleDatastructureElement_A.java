package com.ecotrekker.vehicleconsumption.parser;

import java.security.InvalidParameterException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

abstract public class VehicleDatastructureElement_A {

    @Getter
    @Setter
    @JsonProperty(required = true)
    private String name;

    @Getter
    @Setter
    private Integer co2;

    @Getter
    @Setter
    private Integer kwh;

    @Getter
    @Setter
    @JsonAlias({"parent"})
    private String parentName;


    public static <T extends VehicleDatastructureElement_A> T findByString(List<T> list, String name){
        for (T vehicle : list){

            if (vehicle.getName().compareTo(name) == 0) {
                return vehicle;
            }
        }

        return null;
    }

    public VehicleDatastructureElement_A(){

    }

    public VehicleDatastructureElement_A(String name, Integer co2, Integer kwh_per_pkm, String parent){
        if (name == null){
            new InvalidParameterException("The name of a vehicle datastructure element cannot be null!");
        }
        this.setName(name);
        this.setCo2(co2);
        this.setKwh(kwh_per_pkm);
        this.setParentName(parent);
    }

}