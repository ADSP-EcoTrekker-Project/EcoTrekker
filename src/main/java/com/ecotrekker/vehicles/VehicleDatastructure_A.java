package com.ecotrekker.vehicles;

import java.lang.UnsupportedOperationException;

import com.ecotrekker.config.vehicles.*;

public abstract class VehicleDatastructure_A {

    private VehicleDatastructureElement_A root = null;

    public VehicleDatastructureElement_A getRoot() {
        return root;
    }

    <T extends VehicleDatastructureElement_A> void setRoot(T root) {
        this.root = root;
    }

    public VehicleDatastructureElement_A getElementByName(String name){
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public VehicleDatastructure_A(VehicleConfigLoader_I configLoader){
        
    }

}