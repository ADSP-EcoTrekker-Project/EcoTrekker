package com.ecotrekker.vehicleconsumption.config.vehicles;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTreeElement;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTree;
import com.ecotrekker.vehicleconsumption.parser.implementations.ITomlVehicleConfigLoader;

@Configuration
public class VehicleBeanFactory {
    
    @Value("${configPath}")
    private String pathToConfig;

    private static Logger logger = LoggerFactory.getLogger(VehicleBeanFactory.class);

    @Autowired
    private ITomlVehicleConfigLoader<IVehicleTreeElement, IVehicleTree> tomlConfigLoader;

    @Bean
    public IVehicleTree getVehicleTree() throws Exception{
        Path config = Paths.get(pathToConfig).toAbsolutePath();

        logger.info("Parsing config at " + config.toString());

        IVehicleTree t = tomlConfigLoader.getVehicles(config, IVehicleTree.class, IVehicleTreeElement.class);

        logger.info("Tree: \n" + t.toString());

        return t;
    }

}
