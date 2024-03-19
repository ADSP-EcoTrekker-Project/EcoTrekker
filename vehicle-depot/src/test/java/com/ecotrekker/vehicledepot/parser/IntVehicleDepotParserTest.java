package com.ecotrekker.vehicledepot.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IntVehicleDepotParserTest {

    @Autowired
    private VehicleDepotParser parser;

    private URI getResourceURI(String resourceRelativePath) throws URISyntaxException {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(resourceRelativePath)).toURI();
    }

    private List<VehicleDepot> loadDepotConfig(String resourceRelativePath) throws Exception {
        URI resourceURI = getResourceURI(resourceRelativePath);
        return parser.getDepots(Paths.get(resourceURI), new TomlMapper());
    }

    @Test
    public void simpleConfigTest() throws Exception {
        List<VehicleDepot> depotList = loadDepotConfig("toml/simple.toml");
        assertSingleDepot(depotList.get(0), 4, 0.5, 0.5);
    }

    @ParameterizedTest
    @ValueSource(strings = {"toml/multiple_depots.toml"})
    public void multipleDepotsConfigTest(String resourcePath) throws Exception {
        List<VehicleDepot> depotList = loadDepotConfig(resourcePath);
        assertMultipleDepots(depotList);
    }

    private void assertSingleDepot(VehicleDepot depot, int expectedRoutes, double expectedEBusShare, double expectedIceBusShare) {
        assertEquals(expectedRoutes, depot.getDepotRoutes().size());
        assertTrue(depot.getVehicles().containsKey("e-bus"));
        assertTrue(depot.getVehicles().containsKey("ice-bus"));
        assertEquals(expectedEBusShare, depot.getVehicles().get("e-bus"));
        assertEquals(expectedIceBusShare, depot.getVehicles().get("ice-bus"));
    }

    private void assertMultipleDepots(List<VehicleDepot> depotList) {
        assertEquals(6, depotList.size());
        assertSingleDepot(depotList.get(0), 3, 0.1, 0.9);
        assertSingleDepot(depotList.get(1), 3, 0.4, 0.6);
        assertSingleDepot(depotList.get(2), 6, 0.5, 0.5);
        assertSingleDepot(depotList.get(3), 4, 0.6, 0.4);
        assertSingleDepot(depotList.get(4), 4, 0.7, 0.3);
        assertSingleDepot(depotList.get(5), 4, 0.8, 0.2);
    }

}