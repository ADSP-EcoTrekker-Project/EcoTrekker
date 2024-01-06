package com.ecotrekker.vehicleconsumption.VehicleData;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTreeElement;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.IVehicleTree;
import com.ecotrekker.vehicleconsumption.parser.implementations.ITomlVehicleConfigLoader;

@SpringBootTest
public class TomlVehicleConfigLoaderTest {

    @Autowired
    private ITomlVehicleConfigLoader<IVehicleTreeElement, IVehicleTree> tomlConfigLoader;

    private URI find_resource(String resource_relative_path) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(resource_relative_path).toURI();
    }

    private IVehicleTree load_config_into_tree(String resource_relative_path) throws Exception {
        final URI relative_config = find_resource(resource_relative_path);

        IVehicleTree t = tomlConfigLoader.getVehicles(Paths.get(relative_config), IVehicleTree.class, IVehicleTreeElement.class);

        return t;
    }

    @Test
    public void single_vehicle_simple_config(){
        try {
            IVehicleTree t = load_config_into_tree("tomlloader/single_simple.toml");

            Map<String, IVehicleTreeElement> l = t.asMap();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 1);

            IVehicleTreeElement e = t.getElement("car");

            Assertions.assertTrue(e.getName().compareTo("car") == 0);
            // No kwh are set
            Assertions.assertTrue(e.getKwh() == null);
            // no co2 is set
            Assertions.assertTrue(e.getCo2() == null);
            // The tree parent element is the parent
            Assertions.assertTrue(e.getParent() != null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Loading the config files should not throw an exception!");
        }
    }

    @Test
    public void single_vehicle_complex_config(){
        try {
            IVehicleTree t = load_config_into_tree("tomlloader/single_complex.toml");

            Map<String, IVehicleTreeElement> l = t.asMap();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 1);

            IVehicleTreeElement e = t.getElement("car");

            Assertions.assertTrue(e.getName().compareTo("car") == 0);
            // No kwh are set
            Assertions.assertTrue(e.getKwh() == 50);
            // no co2 is set
            Assertions.assertTrue(e.getCo2() == 50);
            // The tree parent element is the parent
            Assertions.assertTrue(e.getParent() != null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Loading the config files should not throw an exception!");
        }
    }

    @Test
    public void multi_vehicle_simple_config(){
        try {
            IVehicleTree t = load_config_into_tree("tomlloader/multi_simple.toml");

            Map<String, IVehicleTreeElement> l = t.asMap();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 9);

            IVehicleTreeElement e = t.getElement("car");

            Assertions.assertTrue(e != null);

            e = t.getElement("ICE");
            
            Assertions.assertTrue(e != null);

            e = t.getElement("ice-car");
            
            Assertions.assertTrue(e != null);
    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Loading the config files should not throw an exception!");
        }
    }
    
}
