package com.ecotrekker.vehicleconsumption.parser;

import java.lang.UnsupportedOperationException;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;


public abstract class AbstractVehicleDatastructure <T extends AbstractVehicleDatastructureElement> {

    @Getter
    @Setter
    private T root = null;

    public Map<String, T> asMap() {
        new UnsupportedOperationException("Please override in Subtypes");
        return null;
    }

    public void addElement(T element) {
        throw new UnsupportedOperationException("Please override in Subtypes");
    }

    public void removeElement(T element, boolean removeChildren) {
        throw new UnsupportedOperationException("Please override in Subtypes");
    }

    public void removeElement(T element) {
        throw new UnsupportedOperationException("Please override in Subtypes");
    }

    public T getElement(String name) {
        throw new UnsupportedOperationException("Please override in Subtypes");
    }

}
