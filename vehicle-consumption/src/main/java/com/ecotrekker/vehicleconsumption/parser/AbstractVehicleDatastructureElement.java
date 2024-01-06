package com.ecotrekker.vehicleconsumption.parser;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
abstract public class AbstractVehicleDatastructureElement {

    @JsonProperty(required = true)
    private String name;

    private Double co2;

    private Double kwh;

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

    public AbstractVehicleDatastructureElement(String name, Double co2, Double kwh_per_pkm, String parent){
        if (name == null){
            new InvalidParameterException("The name of a vehicle datastructure element cannot be null!");
        }
        this.setName(name);
        this.setCo2(co2);
        this.setKwh(kwh_per_pkm);
        this.setParentName(parent);
    }

}