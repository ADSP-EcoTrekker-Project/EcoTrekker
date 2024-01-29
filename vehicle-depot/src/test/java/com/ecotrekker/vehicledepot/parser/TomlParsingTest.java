package com.ecotrekker.vehicledepot.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;

@SpringBootTest
public class TomlParsingTest {

    @Autowired
    private VehicleDepotParser parser;

    private TomlMapper tomlMapper = new TomlMapper();

    private URI find_resource(String resource_relative_path) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(resource_relative_path).toURI();
    }

    private List<VehicleDepot> loadDepotConfig(String resource_relative_path) throws Exception {
        final URI relative_config = find_resource(resource_relative_path);

        List<VehicleDepot> list = parser.getDepots(Paths.get(relative_config), tomlMapper);

        return list;
    }

    @Test
    public void simpleConfigTest() throws Exception {
        List<VehicleDepot> depotList = loadDepotConfig("toml/simple.toml");

        assertTrue(depotList.size() == 1);

        VehicleDepot depot = depotList.get(0);

        assertTrue(depot.getDepotRoutes().size() == 4);
    }

}
