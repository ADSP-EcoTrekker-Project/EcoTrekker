package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.LinkedList;

import com.ecotrekker.vehicleconsumption.parser.VehicleDatastructureElement_A;

import lombok.Getter;
import lombok.Setter;

public class VehicleTreeElement_C extends VehicleDatastructureElement_A{

    @Getter
    @Setter
    private LinkedList<VehicleTreeElement_C> children;

    @Getter
    @Setter
    private VehicleTreeElement_C parent;

    public VehicleTreeElement_C(String name, Integer g_co2_per_pkm, Integer kwh_per_pkm, String parent) {
        super(name, g_co2_per_pkm, kwh_per_pkm, parent);
        this.children = new LinkedList<>();
        //TODO Auto-generated constructor stub
    }

    @Override
    public int compareTo(VehicleDatastructureElement_A o) {
        return this.getName().compareTo(o.getName());
    }

}