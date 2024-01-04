package com.ecotrekker.vehicleconsumption;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTreeElement_C;
import com.ecotrekker.vehicleconsumption.config.vehicles.tree.VehicleTree_C;
import com.ecotrekker.vehicleconsumption.parser.implementations.TomlVehicleConfigLoader_C;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class TomlVehicleConfigLoader_C_VehicleTree_C_Test {

    private static Logger logger = LoggerFactory.getLogger(TomlVehicleConfigLoader_C_VehicleTree_C_Test.class);

    private URI find_resource(String resource_relative_path) throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(resource_relative_path).toURI();
    }

    private VehicleTree_C load_config_into_tree(String resource_relative_path) throws URISyntaxException, StreamReadException, DatabindException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
        final URI relative_config = find_resource(resource_relative_path);

        TomlVehicleConfigLoader_C l = new TomlVehicleConfigLoader_C(Paths.get(relative_config), VehicleTreeElement_C.class);

        VehicleTree_C t = new VehicleTree_C(l);

        return t;
    }

    @Test
    public void single_vehicle_simple_config(){
        try {
            VehicleTree_C t = load_config_into_tree("tomlloader/single_simple.toml");

            LinkedList<VehicleTreeElement_C> l = t.asList();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 1);

            VehicleTreeElement_C e = t.getElementByName("car");

            Assertions.assertTrue(e.getName().compareTo("car") == 0);
            // No kwh are set
            Assertions.assertTrue(e.getKwh_per_pkm() == null);
            // no co2 is set
            Assertions.assertTrue(e.getG_co2_per_pkm() == null);
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
            VehicleTree_C t = load_config_into_tree("tomlloader/single_complex.toml");

            LinkedList<VehicleTreeElement_C> l = t.asList();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 1);

            VehicleTreeElement_C e = t.getElementByName("car");

            Assertions.assertTrue(e.getName().compareTo("car") == 0);
            // No kwh are set
            Assertions.assertTrue(e.getKwh_per_pkm() == 50);
            // no co2 is set
            Assertions.assertTrue(e.getG_co2_per_pkm() == 50);
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
            VehicleTree_C t = load_config_into_tree("tomlloader/multi_simple.toml");

            LinkedList<VehicleTreeElement_C> l = t.asList();

            Assertions.assertTrue(l.size() > 0);

            Assertions.assertTrue(l.size() == 9);

            VehicleTreeElement_C e = t.getElementByName("car");

            Assertions.assertTrue(e != null);

            e = t.getElementByName("ICE");
            
            Assertions.assertTrue(e != null);

            e = t.getElementByName("ice-car");
            
            Assertions.assertTrue(e != null);
    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Loading the config files should not throw an exception!");
        }
    }
    
}
