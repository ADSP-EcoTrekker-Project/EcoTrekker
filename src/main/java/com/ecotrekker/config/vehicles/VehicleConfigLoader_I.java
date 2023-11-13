package com.ecotrekker.config.vehicles;

import java.util.LinkedList;

import com.ecotrekker.vehicles.VehicleDatastructureElement_A;

public interface VehicleConfigLoader_I extends Iterable<VehicleDatastructureElement_A> {

    public LinkedList<VehicleDatastructureElement_A> getVehicle_elements();

}
