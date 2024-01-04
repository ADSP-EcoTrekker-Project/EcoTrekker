package com.ecotrekker.vehicleconsumption.parser;

import java.util.LinkedList;

public interface VehicleConfigLoader_I <T extends VehicleDatastructureElement_A> extends Iterable<T> {

    public LinkedList<T> getVehicles();

}
