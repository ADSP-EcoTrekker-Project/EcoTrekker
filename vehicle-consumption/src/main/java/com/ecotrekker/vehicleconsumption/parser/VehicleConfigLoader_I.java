package com.ecotrekker.vehicleconsumption.parser;

import java.util.LinkedList;

public interface VehicleConfigLoader_I extends Iterable<VehicleDatastructureElement_A> {

    public <T extends VehicleDatastructureElement_A> LinkedList<T> getVehicle_elements();

}
