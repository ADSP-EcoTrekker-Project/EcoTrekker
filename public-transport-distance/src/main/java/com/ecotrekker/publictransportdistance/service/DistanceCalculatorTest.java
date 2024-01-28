package com.ecotrekker.publictransportdistance.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DistanceCalculatorTest {

    private PublicTransportDistanceService distanceService;

    @BeforeEach
    public void setUp() throws IOException {
        String filePath = "/Users/yassinelazreg/IdeaProjects/EcoTrekker/public-transport-distance/src/main/resources/data_modified_imputed.json";
        Resource resource = new FileSystemResource(filePath);
        distanceService = new PublicTransportDistanceService(resource);
    }

    @Test
    public void testDistanceCalculation() {
        //test1
        String start = "S+U panKoW (Berlin)";
        String end = "s SchöneFeld (bei Berlin) Bhf";
        String vehicle = "S85";
        long startTime = System.currentTimeMillis();
        distanceService.calculateDistance(start, end, vehicle);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);

        //test2
        String start1 = "S Lankwitz";
        String end1 = "S Lichtenrade (Berlin)";
        String vehicle1 = "S2B";
        long startTime1 = System.currentTimeMillis();
        distanceService.calculateDistance(start1, end1, vehicle1);
        long stopTime1 = System.currentTimeMillis();
        long elapsedTime1 = stopTime1 - startTime1;
        System.out.println(elapsedTime1);
    }

}
