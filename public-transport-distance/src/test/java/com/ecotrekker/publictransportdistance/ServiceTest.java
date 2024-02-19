package com.ecotrekker.publictransportdistance;

import com.ecotrekker.publictransportdistance.service.PublicTransportDistanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private PublicTransportDistanceService distanceService;

    @Test
    public void testDistanceCalculation() {

        //test1
        String start = "S+U Pankow (Berlin)";
        String end = "S Schönefeld (bei Berlin) Bhf";
        String vehicle = "S85";
        long startTime = System.currentTimeMillis();
        distanceService.calculateDistance(start, end, vehicle);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);

        //test2
        String start1 = "S Lankwitz (Berlin)";
        String end1 = "S Lichtenrade (Berlin)";
        String vehicle1 = "S2B";
        long startTime1 = System.currentTimeMillis();
        distanceService.calculateDistance(start1, end1, vehicle1);
        long stopTime1 = System.currentTimeMillis();
        long elapsedTime1 = stopTime1 - startTime1;
        System.out.println(elapsedTime1);
    }

}
