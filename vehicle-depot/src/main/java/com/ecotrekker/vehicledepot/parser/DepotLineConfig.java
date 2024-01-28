package com.ecotrekker.vehicledepot.parser;

import java.util.List;

import lombok.Data;

@Data
public class DepotLineConfig {
    
    private List<DepotVehicle> vehicles;

    private List<String> lines;
}
