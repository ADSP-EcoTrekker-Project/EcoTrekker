package com.ecotrekker.vehicleconsumption.config.vehicles;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTreeElement_C;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTree_C;
import com.ecotrekker.vehicleconsumption.parser.implementations.TomlVehicleConfigLoader_C;

@Configuration
public class VehicleBeanFactory_C {
    
    @Value(value = "${config}")
    private String pathToConfig;

    private static Logger logger = LoggerFactory.getLogger(VehicleBeanFactory_C.class);

    @Bean
    public VehicleTree_C<TomlVehicleConfigLoader_C<VehicleTreeElement_C>> getVehicleTree() throws Exception{
        Path config = Paths.get(pathToConfig).toAbsolutePath();

        logger.info("Parsing config at " + config.toString());

        TomlVehicleConfigLoader_C<VehicleTreeElement_C> l = new TomlVehicleConfigLoader_C<>(config, VehicleTreeElement_C.class);

        VehicleTree_C<TomlVehicleConfigLoader_C<VehicleTreeElement_C>> t = new VehicleTree_C<>(l);

        logger.info("Tree: \n" + t.toString());

        return t;
    }

}
