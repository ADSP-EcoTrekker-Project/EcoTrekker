package com.ecotrekker.config.vehicles;

import com.ecotrekker.vehicles.VehicleTreeElement_C;
import com.ecotrekker.vehicles.VehicleTree_C;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

public class TOMLVehicleConfigLoader_C_Tests {

    private static Logger logger = LoggerFactory.getLogger(TOMLVehicleConfigLoader_C_Tests.class);

    @Test
    public void relative_includes(){
        try {
            final URI relative_config = Thread.currentThread().getContextClassLoader().getResource("config/vehicles/relative_config.toml").toURI();

            TOMLVehicleConfigLoader_C l = new TOMLVehicleConfigLoader_C(Paths.get(relative_config), VehicleTreeElement_C.class);

            VehicleTree_C t = new VehicleTree_C(l);

            logger.info(t.getElementByName("car").toString());

            Assertions.assertTrue(l.getVehicle_elements().size() > 0);
            
        } catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail("Should not throw exception");
        }
    }

    @Test
    public void load_bad_file(){
        try {
            TOMLVehicleConfigLoader_C l = new TOMLVehicleConfigLoader_C(Paths.get("null.toml"), VehicleTreeElement_C.class);

            Assertions.assertTrue(l.getVehicle_elements().size() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Should not throw exception");
        }
    }

    @AfterEach
    public void flushSystemOut(){
        System.out.flush();
    }
    
}
