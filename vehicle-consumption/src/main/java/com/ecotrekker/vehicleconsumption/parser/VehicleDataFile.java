package com.ecotrekker.vehicleconsumption.parser;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

public class VehicleDataFile <T extends AbstractVehicleDatastructureElement> {
    
    @JsonAlias({"include"})
    @Getter
    @Setter
    private ArrayList<String> includes;

    @JsonAlias({"vehicle"})
    @Getter
    @Setter
    private ArrayList<T> vehicles;
}
