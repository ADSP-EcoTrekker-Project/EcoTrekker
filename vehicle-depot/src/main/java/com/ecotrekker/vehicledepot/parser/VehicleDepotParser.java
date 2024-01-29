package com.ecotrekker.vehicledepot.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class VehicleDepotParser {
    
    public List<VehicleDepot> getDepots(Path pathToConfig, ObjectMapper mapper) {
        List<VehicleDepot> results = new LinkedList<>();

        LinkedList<Path> knownConfigFiles = new LinkedList<Path>();
        Stack<Path> todo = new Stack<>();
        todo.push(pathToConfig.toAbsolutePath());

        while (todo.size() > 0){
            Path path = todo.pop();

            // Check if file was already parsed
            for (Path p : knownConfigFiles){
                if (p.compareTo(path) == 0) {
                    log.warn("You tried to parse " + path + " again!");
                    continue;
                }
            }

            // Try to map file to Objects
            VehicleDepotConfigFile data;
            try {
                data = mapper.readValue(path.toFile(), VehicleDepotConfigFile.class);
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
            if (data.getDepots() != null) {
                for (DepotLineConfig depot : data.getDepots()) {
                    VehicleDepot newDepot = new VehicleDepot();
                    Map<String, Double> vehicleMap = new HashMap<>();
                    for (DepotVehicle vehicle : depot.getVehicles()) {
                        vehicleMap.put(vehicle.getName(), vehicle.getShare());
                    }
                    newDepot.setVehicles(vehicleMap);
                    for (String line : depot.getLines()) {
                        TransportLine newLine = new TransportLine();
                        newLine.setName(line);
                        newLine.setDepot(newDepot);
                        newDepot.addRoute(
                            newLine
                        );
                    }
                    results.add(newDepot);
                }
                
            }

            // Mark the file as already parsed
            knownConfigFiles.add(path);
        }

        return results;
    }

}
