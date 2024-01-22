package com.ecotrekker.vehicledepot.config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.ecotrekker.vehicledepot.parser.VehicleDepotParser;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;

@Configuration
public class DepotConfig {

    @Value("${config}")
    private String pathToConfig;
    
    @Autowired
    private VehicleDepotParser depotParser;

    private TomlMapper tomlMapper = new TomlMapper();

    @Bean
    public Map<String, TransportLine> getLineMap() {
        Map<String, TransportLine> result = new HashMap<>();
        List<VehicleDepot> depots = depotParser.getDepots(Path.of(pathToConfig), tomlMapper);

        for (VehicleDepot depot : depots) {
            result.putAll(depot.getDepotRoutes());
        }

        return result;
    }
    
}
