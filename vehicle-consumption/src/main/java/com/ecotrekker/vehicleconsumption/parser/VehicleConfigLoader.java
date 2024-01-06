package com.ecotrekker.vehicleconsumption.parser;

public interface VehicleConfigLoader <E extends AbstractVehicleDatastructureElement, T extends AbstractVehicleDatastructure<E>> {

    public T getVehicles(Class<T> vehiclesClass, Class<E> typeParameterClass) throws Exception;

}
