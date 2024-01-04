package com.ecotrekker.vehicleconsumption.parser;

import java.lang.UnsupportedOperationException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public abstract class VehicleDatastructure_A<T extends VehicleDatastructureElement_A> {

    @Getter
    @Setter
    private T root = null;


    public List<T> asList(){
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public T getElementByName(String name){
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public VehicleDatastructure_A(VehicleConfigLoader_I configLoader){

    }

}
