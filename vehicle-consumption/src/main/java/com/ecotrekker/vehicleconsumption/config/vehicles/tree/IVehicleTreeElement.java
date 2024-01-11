package com.ecotrekker.vehicleconsumption.config.vehicles.tree;

import java.util.LinkedList;

import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructureElement;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class IVehicleTreeElement extends AbstractVehicleDatastructureElement{

    @JsonIgnore
    private LinkedList<IVehicleTreeElement> children;

    @JsonIgnore
    private IVehicleTreeElement parent;

    public IVehicleTreeElement(){
        super();
        this.children = new LinkedList<>();
    }

    public IVehicleTreeElement(String name, Double g_co2_per_pkm, Double kwh_per_pkm, String parent) {
        super(name, g_co2_per_pkm, kwh_per_pkm, parent);
        this.children = new LinkedList<>();
    }

    public int numParents(){
        IVehicleTreeElement e = this.parent;
        int num = 0;
        while (e != null) {
            num += 1;
            e = e.parent;
        }
        return num;
    }

}