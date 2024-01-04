package com.ecotrekker.vehicleconsumption.parser;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

abstract public class AbstractVehicleDatastructureElement {

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


    public static <T extends AbstractVehicleDatastructureElement> T findByString(List<T> list, String name) throws NoSuchElementException {
        for (T vehicle : list){

            if (vehicle.getName().compareTo(name) == 0) {
                return vehicle;
            }
        }
        throw new NoSuchElementException("Could not find the requested Vehicle");
    }

    public AbstractVehicleDatastructureElement(){

    }

    public AbstractVehicleDatastructureElement(String name, Integer co2, Integer kwh_per_pkm, String parent){
        if (name == null){
            new InvalidParameterException("The name of a vehicle datastructure element cannot be null!");
        }
        this.setName(name);
        this.setCo2(co2);
        this.setKwh(kwh_per_pkm);
        this.setParentName(parent);
    }

}