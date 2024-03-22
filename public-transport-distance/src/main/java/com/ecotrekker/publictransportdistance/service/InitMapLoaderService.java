package com.ecotrekker.publictransportdistance.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecotrekker.publictransportdistance.model.PublicTransportRoutes;
import com.ecotrekker.publictransportdistance.model.VehicleRoute;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitMapLoaderService implements CommandLineRunner {

    @Autowired
    private PubTransportService pubTransportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${configPath}")
    private String configPath;

    private Map<String, VehicleRoute> loadPubTransportData() throws Exception {
        log.info("Loading routes from " + configPath);
        if (configPath == null || configPath.isEmpty()) {
            throw new IllegalArgumentException("Config path is null or empty");
        }
        Path configPath = Paths.get(this.configPath).toAbsolutePath();
        File configFile = configPath.toFile();
        PublicTransportRoutes routes = objectMapper.readValue(configFile, PublicTransportRoutes.class);
        return routes != null ? routes.getVehicles() : Collections.emptyMap();
    }

    @Override
    public void run(String... args) throws Exception {
        pubTransportService.loadMapData(loadPubTransportData());
    }
}