package com.ecotrekker.vehicledepot.parser;

import java.util.List;

import lombok.Data;

@Data
public class DepotLineConfig {
    
    List<DepotVehicle> vehicles;

    List<String> lines;
}
