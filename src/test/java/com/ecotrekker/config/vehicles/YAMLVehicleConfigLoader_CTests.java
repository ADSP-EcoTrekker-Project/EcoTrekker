package com.ecotrekker.config.vehicles;

import com.ecotrekker.vehicles.VehicleTreeElement_C;
import com.ecotrekker.vehicles.VehicleTree_C;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class YAMLVehicleConfigLoader_CTests {

    @Test
    public void test_if_file_can_be_opened(){
        try {
            YAMLVehicleConfigLoader_C l = new YAMLVehicleConfigLoader_C(Paths.get("G:/ADSP/EcoTrekker/src/test/resources/simple_vehicle_config.toml"), VehicleTreeElement_C.class);

            VehicleTree_C t = new VehicleTree_C(l);

            System.out.println(t.getElementByName("car").toString());

            System.out.flush();
        } catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assertions.fail("Should not throw exception");
        }
    }
    
}
