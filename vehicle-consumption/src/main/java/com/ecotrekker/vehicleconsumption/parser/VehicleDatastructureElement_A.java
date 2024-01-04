package com.ecotrekker.vehicleconsumption.parser;

import java.security.InvalidParameterException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

abstract public class VehicleDatastructureElement_A implements Comparable<VehicleDatastructureElement_A>{

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer g_co2_per_pkm;
    @Getter
    @Setter
    private Integer kwh_per_pkm;
    @Getter
    @Setter
    /*
     * If the parent is missing `parent_string` should be null
     */
    private String parent_string;


    public static <T extends VehicleDatastructureElement_A> T findByString(List<T> list, String name){
        for (T vehicle : list){

            if (vehicle.getName().compareTo(name) == 0) {
                return vehicle;
            }
        }

        return null;
    }

    public VehicleDatastructureElement_A(String name, Integer g_co2_per_pkm, Integer kwh_per_pkm, String parent){
        if (name == null){
            new InvalidParameterException("The name of a vehicle datastructure element cannot be null!");
        }
        this.setName(name);
        this.setG_co2_per_pkm(g_co2_per_pkm);
        this.setKwh_per_pkm(kwh_per_pkm);
        this.setParent_string(parent);
    }

}