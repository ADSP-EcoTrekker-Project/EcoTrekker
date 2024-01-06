package com.ecotrekker.vehicleconsumption.parser.implementations;

import java.util.Stack;
import java.util.LinkedList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ecotrekker.vehicleconsumption.parser.VehicleConfigLoader;
import com.ecotrekker.vehicleconsumption.parser.VehicleDataFile;
import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructure;
import com.ecotrekker.vehicleconsumption.parser.AbstractVehicleDatastructureElement;
import com.fasterxml.jackson.dataformat.toml.TomlMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ITomlVehicleConfigLoader <E extends AbstractVehicleDatastructureElement, T extends AbstractVehicleDatastructure<E>> implements VehicleConfigLoader<E, T> {

    @Override
    public T getVehicles(Path pathToConfig, Class<T> vehiclesClass, Class<E> typeParameterClass) throws Exception{
        LinkedList<Path> knownVehicleConfigFiles = new LinkedList<Path>();
        Stack<Path> todo = new Stack<>();
        todo.push(pathToConfig.toAbsolutePath());

        TomlMapper mapper = new TomlMapper();

        T vehicles = vehiclesClass.getDeclaredConstructor().newInstance();

        while (todo.size() > 0){
            Path path = todo.pop();

            // Check if file was already parsed
            for (Path p : knownVehicleConfigFiles){
                if (p.compareTo(path) == 0) {
                    log.warn("You tried to parse " + path + " again!");
                    continue;
                }
            }

            // Try to map file to Objects
            VehicleDataFile<E> data;
            try {
                data = mapper.readValue(path.toFile(), mapper.getTypeFactory().constructParametricType(VehicleDataFile.class, typeParameterClass));
            } catch (IOException e) {
                e.printStackTrace();
                log.warn("Error when parsing " + path + ". Skipped!");
                continue;
            }

            // Check if more files are included
            if (data.getIncludes() != null) {
                for (String new_path : data.getIncludes()) {
                    Path n_p = Paths.get(new_path);
                    if (n_p.isAbsolute() == false)
                        n_p = Paths.get(path.getParent().toAbsolutePath().toString(), n_p.toString());
                    todo.push(n_p);
                }
            }

            // Add parsed vehicles to List
            if (data.getVehicles() != null) {
                for (E vehicle : data.getVehicles()){
                    vehicles.addElement(vehicle);
                }
            }

            // Mark the file as already parsed
            knownVehicleConfigFiles.add(path);
        }

        return vehicles;
    }
    
}
