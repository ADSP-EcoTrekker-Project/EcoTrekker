package com.ecotrekker.vehicles;

import java.util.LinkedList;

public class VehicleTreeElement_C extends VehicleDatastructureElement_A{

    public VehicleTreeElement_C(String name, Integer g_co2_per_pkm, Integer kwh_per_pkm, String parent) {
        super(name, g_co2_per_pkm, kwh_per_pkm, parent);
        children = new LinkedList<>();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int compareTo(VehicleDatastructureElement_A o) {
        return this.getName().compareTo(o.getName());
    }

}