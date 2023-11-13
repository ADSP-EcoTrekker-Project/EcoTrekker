package com.ecotrekker.vehicles;

import com.ecotrekker.config.vehicles.*;

public abstract class VehicleDatastructure_A {

    private VehicleTreeElement_C root = null;

    public VehicleTreeElement_C getRoot() {
        return root;
    }

    void setRoot(VehicleTreeElement_C root) {
        this.root = root;
    }

    

    public VehicleDatastructure_A(VehicleConfigLoader_I configLoader){
        
    }

}