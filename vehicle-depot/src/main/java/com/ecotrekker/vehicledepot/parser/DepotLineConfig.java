package com.ecotrekker.vehicledepot.parser;

import java.util.List;

import lombok.Data;

@Data
public class DepotLineConfig {
    
    Double shareElectrical;

    List<String> lines;
}
