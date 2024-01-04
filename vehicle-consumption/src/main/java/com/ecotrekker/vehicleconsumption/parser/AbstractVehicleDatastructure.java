package com.ecotrekker.vehicleconsumption.parser;

import java.lang.UnsupportedOperationException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public abstract class AbstractVehicleDatastructure <T extends AbstractVehicleDatastructureElement> {

    @Getter
    @Setter
    private T root = null;


    public List<T> asList(){
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public T getElementByName(String name) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Please override in Subtypes");
    }

    public AbstractVehicleDatastructure(VehicleConfigLoader<T> configLoader){

    }

}
