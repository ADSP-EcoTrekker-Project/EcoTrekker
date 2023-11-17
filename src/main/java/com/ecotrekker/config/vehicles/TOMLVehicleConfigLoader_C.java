package com.ecotrekker.config.vehicles;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.ecotrekker.vehicles.VehicleDatastructureElement_A;



public class TOMLVehicleConfigLoader_C implements VehicleConfigLoader_I {

    private static Logger logger = LoggerFactory.getLogger(TOMLVehicleConfigLoader_C.class);

    private static LinkedList<Path> known_vehicle_config_files = new LinkedList<Path>();

    ArrayList<Map<String,Object>> vehicles = new ArrayList<Map<String,Object>>();

    private LinkedList<VehicleDatastructureElement_A> vehicle_elements = new LinkedList<VehicleDatastructureElement_A>();

    public LinkedList<VehicleDatastructureElement_A> getVehicle_elements() {
        return vehicle_elements;
    }

    /**
     * recursively loads config files in TOML format 
     * @param pathToFile The Path object pointing to the top level config file
     * @return An ArrayList containing a map for each vehicle to be parsed into an object
     */
    public ArrayList<Map<String,Object>> loadConfigFile(Path pathToFile) {    
        if (known_vehicle_config_files.contains(pathToFile)){
            logger.warn("There is a circular include in your vehicle config files!");
            logger.warn(String.format("You just tried to parse %s again!", pathToFile.toString()));
        }

        TomlMapper tomlMapper = new TomlMapper();
        File tomlFile = pathToFile.toFile();
        ArrayList<Map<String,Object>> vehicles = new ArrayList<>();
        Map<String, Object> data;
        try{
            data = tomlMapper.readValue(tomlFile, Map.class);
        }
        catch (IOException e){
            logger.error(String.format("Failed to parse %s", pathToFile.toString()));
            return vehicles;
        }

        // Load Vehicles
        if (data.get("vehicles") != null){
            ArrayList<Map<String,Object>> vehicle_data = (ArrayList<Map<String,Object>>) data.get("vehicles");
            vehicles.addAll(vehicle_data);
        }

        // Load included files and recursively parse them
        if (data.get("include") != null){
            ArrayList<String> files = (ArrayList<String>) data.get("include");
            for (String file : files){
                Path tpath = Paths.get(file);
                if (tpath.isAbsolute() == false){
                    tpath = Paths.get(pathToFile.getParent().toAbsolutePath().toString(), file);
                }
                ArrayList<Map<String,Object>> tvehicles = loadConfigFile(tpath);
                vehicles.addAll(tvehicles);
            }
        }
        return vehicles;
    }

    /**
     * The TOMLVehicleConfigLoader_C loads a TOML file and has a field for a List of vehicle objects
     * @param <T> The subtype of VehicleDatastructureElement_A that should be created
     * @param pathToFile The path to the root config file
     * @param desiredObject The subtype of VehicleDatastructureElement_A that should be created
     * @throws StreamReadException
     * @throws DatabindException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public <T extends VehicleDatastructureElement_A> TOMLVehicleConfigLoader_C(Path pathToFile, Class<T> desiredObject) throws StreamReadException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        vehicles = this.loadConfigFile(pathToFile);
        for (Map<String, Object> vehicle : vehicles){
            String name = (String) vehicle.get("name");
            Integer g_co2_per_pkm = (Integer) vehicle.get("co2");
            Integer kwh_per_pkm = (Integer) vehicle.get("kwh");
            String parent = (String) vehicle.get("parent");
            Class[] cArg = new Class[4];
            cArg[0] = String.class;
            cArg[1] = Integer.class;
            cArg[2] = Integer.class;
            cArg[3] = String.class;
            Constructor<T> desiredObjectConstructor = desiredObject.getDeclaredConstructor(cArg);
            T vehicle_element = desiredObjectConstructor.newInstance(
                name, g_co2_per_pkm, kwh_per_pkm, parent
            );
            vehicle_elements.add(
                vehicle_element
            );
        }
    }

    @Override
    public Iterator<VehicleDatastructureElement_A> iterator() {
        return vehicle_elements.iterator();
    }



    
}
