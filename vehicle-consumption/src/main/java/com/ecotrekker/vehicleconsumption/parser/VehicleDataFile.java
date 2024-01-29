package com.ecotrekker.vehicleconsumption.parser;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class VehicleDataFile <T extends AbstractVehicleDatastructureElement> {
    
    @JsonAlias({"include"})
    private ArrayList<String> includes;

    @JsonAlias({"vehicle"})
    private ArrayList<T> vehicles;
}
