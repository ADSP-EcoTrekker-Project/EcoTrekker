package com.ecotrekker.vehicleconsumption.parser;

import java.nio.file.Path;

public interface VehicleConfigLoader <E extends AbstractVehicleDatastructureElement, T extends AbstractVehicleDatastructure<E>> {

    public T getVehicles(Path pathToConfig, Class<T> vehiclesClass, Class<E> typeParameterClass) throws Exception;

}
