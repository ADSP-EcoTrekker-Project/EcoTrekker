package com.ecotrekker.vehicles;

import java.security.InvalidParameterException;
import java.util.List;

abstract public class VehicleDatastructureElement_A implements Comparable<VehicleDatastructureElement_A>{

    private String name;
    private Integer g_co2_per_pkm;
    private Integer kwh_per_pkm;
    private String parent_string;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Integer getG_co2_per_pkm() {
        return g_co2_per_pkm;
    }

    void setG_co2_per_pkm(Integer g_co2_per_pkm) {
        this.g_co2_per_pkm = g_co2_per_pkm;
    }

    public Integer getKwh_per_pkm() {
        return kwh_per_pkm;
    }

    void setKwh_per_pkm(Integer kwh_per_pkm) {
        this.kwh_per_pkm = kwh_per_pkm;
    }

    public String getParent_string() {
        return parent_string;
    }

    void setParent_string(String parent_string) {
        this.parent_string = parent_string;
    }

    public static <T extends VehicleDatastructureElement_A> T findByString(List<T> list, String name){
        for (T vehicle : list){

            if (vehicle.getName().compareTo(name) == 0) {
                return vehicle;
            }
        }

        return null;
    }

    VehicleDatastructureElement_A parent;

    List<VehicleDatastructureElement_A> children;

    public VehicleDatastructureElement_A(String name, Integer g_co2_per_pkm, Integer kwh_per_pkm, String parent){
        if (name == null){
            new InvalidParameterException("The name of a vehicle datastructure element cannot be null!");
        }
        this.setName(name);
        this.setG_co2_per_pkm(g_co2_per_pkm);
        this.setKwh_per_pkm(kwh_per_pkm);
        this.setParent_string(parent);
        parent = null;
    }

}