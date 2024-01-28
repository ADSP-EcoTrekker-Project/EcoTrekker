package com.ecotrekker.vehicledepot.parser;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class VehicleDepotConfigFile {
    @JsonAlias({"include"})
    private ArrayList<String> includes;

    @JsonAlias({"depot"})
    private ArrayList<DepotLineConfig> depots;
}
