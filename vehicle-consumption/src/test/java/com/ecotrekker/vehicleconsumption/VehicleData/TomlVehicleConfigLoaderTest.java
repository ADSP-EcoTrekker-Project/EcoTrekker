package com.ecotrekker.vehicleconsumption.VehicleData;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTree;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTreeElement;
import com.ecotrekker.vehicleconsumption.parser.implementations.ITomlVehicleConfigLoader;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TomlVehicleConfigLoaderTest {

    @Autowired
    private ITomlVehicleConfigLoader<IVehicleTreeElement, IVehicleTree> tomlConfigLoader;

    private URI findResource(String resourceRelativePath) throws URISyntaxException {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(resourceRelativePath)).toURI();
    }

    private IVehicleTree loadConfigIntoTree(String resource_relative_path) throws Exception {
        final URI relative_config = findResource(resource_relative_path);

        IVehicleTree t = tomlConfigLoader.getVehicles(Paths.get(relative_config), IVehicleTree.class, IVehicleTreeElement.class);

        return t;
    }

    @Test
    public void singleVehicleSimpleConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/single_simple.toml");

        Map<String, IVehicleTreeElement> l = t.asMap();

        assertFalse(l.isEmpty());
        assertEquals(1, l.size());

        IVehicleTreeElement e = t.getElement("car");

        assertEquals("car", e.getName());
        assertNull(e.getKwh());
        assertNull(e.getCo2());
        assertNotNull(e.getParent());
    }

    @Test
    public void singleVehicleComplexConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/single_complex.toml");

        Map<String, IVehicleTreeElement> l = t.asMap();

        assertFalse(l.isEmpty());
        assertEquals(1, l.size());

        IVehicleTreeElement e = t.getElement("car");

        assertEquals("car", e.getName());
        assertEquals(50, e.getKwh());
        assertEquals(50, e.getCo2());
        assertNotNull(e.getParent());
    }

    @Test
    public void multiVehicleSimpleConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/multi_simple.toml");

        Map<String, IVehicleTreeElement> l = t.asMap();

        assertFalse(l.isEmpty());
        assertEquals(9, l.size());

        assertNotNull(t.getElement("car"));
        assertNotNull(t.getElement("ICE"));
        assertNotNull(t.getElement("ice-car"));
    }

    @Test
    public void emptyConfigFile() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/empty_config.toml");
        assertTrue(t.asMap().isEmpty());
    }

    @Test
    public void nonExistentConfigFile() {
        assertThrows(NullPointerException.class, () -> loadConfigIntoTree("tomlloader/nonexistent.toml"));
    }

    @Test
    public void nestedVehiclesConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/nested_vehicles.toml");

        assertFalse(t.asMap().isEmpty());
        assertEquals(3, t.asMap().size());

        assertNotNull(t.getElement("Parent"));

        IVehicleTreeElement child = t.getElement("Child");
        assertNotNull(child);
        assertNotNull(child.getParent());
        assertEquals("Parent", child.getParent().getName());

        IVehicleTreeElement grandchild = t.getElement("Grandchild");
        assertNotNull(grandchild);
        assertNotNull(grandchild.getParent());
        assertEquals("Child", grandchild.getParent().getName());
    }

    @Test
    public void missingRequiredFieldsConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/missing_fields.toml");
        assertTrue(t.asMap().isEmpty());
    }

    @Test
    public void specialCharactersConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/special_characters.toml");
        assertNotNull(t.getElement("Vehicle with special characters: !@#$%^&*()_-+=[]{};:~"));
    }

    @Test
    public void largeConfig() throws Exception {
        IVehicleTree t = loadConfigIntoTree("tomlloader/large_config.toml");

        Map<String, IVehicleTreeElement> vehicles = t.asMap();

        assertFalse(vehicles.isEmpty());

        // Check some specific vehicles to ensure they're loaded correctly
        assertTrue(vehicles.containsKey("car"));
        assertTrue(vehicles.containsKey("tram"));
        assertTrue(vehicles.containsKey("train"));

        // Assert properties of specific vehicles
        IVehicleTreeElement vehicle1 = vehicles.get("car");
        assertEquals("car", vehicle1.getName());
        assertEquals(100, vehicle1.getKwh());
        assertEquals(50, vehicle1.getCo2());

        IVehicleTreeElement vehicle2 = vehicles.get("tram");
        assertEquals("tram", vehicle2.getName());
        assertEquals(150, vehicle2.getKwh());
        assertEquals(75, vehicle2.getCo2());

        IVehicleTreeElement vehicle3 = vehicles.get("train");
        assertEquals("train", vehicle3.getName());
        assertEquals(200, vehicle3.getKwh());
        assertEquals(100, vehicle3.getCo2());

    }

}