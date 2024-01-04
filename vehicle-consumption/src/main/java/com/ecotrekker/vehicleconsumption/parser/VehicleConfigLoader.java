package com.ecotrekker.vehicleconsumption.parser;

import java.util.LinkedList;

public interface VehicleConfigLoader <T extends AbstractVehicleDatastructureElement> extends Iterable<T> {

    public LinkedList<T> getVehicles();

}
