package com.ecotrekker.vehicledepot.parser;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UnitVehicleDepotParserTest {

    private VehicleDepotParser parser;

    @BeforeEach
    public void setUp() {
        parser = new VehicleDepotParser();
    }

    @Test
    public void getDepotsTest() throws Exception {
        List<VehicleDepot> depotList = parser.getDepots(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("toml/simple.toml")).toURI()),
                new TomlMapper()
        );

        // Assertions
        assertEquals(1, depotList.size());

        VehicleDepot depot = depotList.get(0);
        Map<String, Double> vehicles = depot.getVehicles();
        assertEquals(2, vehicles.size());
        assertEquals(0.5, vehicles.get("e-bus"), 0.00001);
        assertEquals(0.5, vehicles.get("ice-bus"), 0.00001);

        Map<String, TransportLine> depotRoutes = depot.getDepotRoutes();
        assertEquals(4, depotRoutes.size());

        for (String key : List.of("169", "X69", "165", "164")) {
            assertTrue(depotRoutes.containsKey(key), "Key " + key + " not found in depot routes");
        }

        for (String key : List.of("132", "456", "789")) {
            assertFalse(depotRoutes.containsKey(key), "Key " + key + " found in depot routes");
        }
    }
}
