package com.ecotrekker.vehicles;

import java.lang.UnsupportedOperationException;

import com.ecotrekker.config.vehicles.*;

public abstract class VehicleDatastructure_A {

    private VehicleTreeElement_C root = null;

    public VehicleTreeElement_C getRoot() {
        return root;
    }

    void setRoot(VehicleTreeElement_C root) {
        this.root = root;
    }

    public VehicleDatastructureElement_A getElementByName(String name){
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public VehicleDatastructure_A(VehicleConfigLoader_I configLoader){
        
    }

}