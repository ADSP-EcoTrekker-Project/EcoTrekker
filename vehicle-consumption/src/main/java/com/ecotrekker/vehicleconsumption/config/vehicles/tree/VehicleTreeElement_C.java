package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.LinkedList;

import com.ecotrekker.vehicleconsumption.parser.VehicleDatastructureElement_A;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class VehicleTreeElement_C extends VehicleDatastructureElement_A{

    @Getter
    @Setter
    @JsonIgnore
    private LinkedList<VehicleTreeElement_C> children;

    @Getter
    @Setter
    @JsonIgnore
    private VehicleTreeElement_C parent;

    public VehicleTreeElement_C(){
        super();
        this.children = new LinkedList<>();
    }

    public VehicleTreeElement_C(String name, Integer g_co2_per_pkm, Integer kwh_per_pkm, String parent) {
        super(name, g_co2_per_pkm, kwh_per_pkm, parent);
        this.children = new LinkedList<>();
        //TODO Auto-generated constructor stub
    }

    public int numParents(){
        VehicleTreeElement_C e = this.parent;
        int num = 0;
        while (e != null) {
            num += 1;
            e = e.parent;
        }
        return num;
    }

}