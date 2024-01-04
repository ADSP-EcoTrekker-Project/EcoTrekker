package com.ecotrekker.vehicleconsumption.parser.implementations;

import java.util.Iterator;
import java.util.Stack;
import java.util.LinkedList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ecotrekker.vehicleconsumption.parser.VehicleConfigLoader_I;
import com.ecotrekker.vehicleconsumption.parser.VehicleDataFile_C;
import com.ecotrekker.vehicleconsumption.parser.VehicleDatastructureElement_A;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;

import lombok.Getter;

public class TomlVehicleConfigLoader_C <T extends VehicleDatastructureElement_A> implements VehicleConfigLoader_I<T> {

    private Logger logger = LoggerFactory.getLogger(TomlVehicleConfigLoader_C.class);

    private LinkedList<Path> known_vehicle_config_files = new LinkedList<Path>();

    private Stack<Path> todo = new Stack<>();

    @Getter
    private LinkedList<T> vehicles = new LinkedList<T>();

    @Override
    public Iterator<T> iterator() {
        return vehicles.iterator();
    }

    public TomlVehicleConfigLoader_C(Path pathToConfig, Class<T> typeParameterClass) {
        todo.push(pathToConfig.toAbsolutePath());
        
        
        TomlMapper mapper = new TomlMapper();

        while (todo.size() > 0){
            Path path = todo.pop();

            for (Path p : known_vehicle_config_files){
                if (p.compareTo(path) == 0) {
                    logger.warn("You tried to parse " + path + " again!");
                    continue;
                }
            }

            VehicleDataFile_C<T> data;
            try {
                data = mapper.readValue(path.toFile(), mapper.getTypeFactory().constructParametricType(VehicleDataFile_C.class, typeParameterClass));
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("Error when parsing " + path + ". Skipped!");
                continue;
            }

            if (data.getIncludes() != null) {
                for (String new_path : data.getIncludes()) {
                    Path n_p = Paths.get(new_path);
                    if (n_p.isAbsolute() == false)
                        n_p = Paths.get(path.getParent().toAbsolutePath().toString(), n_p.toString());
                    todo.push(n_p);
                }
            }

            if (data.getVehicles() != null) {
                for (T vehicle : data.getVehicles()){
                    vehicles.add(vehicle);
                }
            }

            known_vehicle_config_files.add(path);
        }
    }
    
}
