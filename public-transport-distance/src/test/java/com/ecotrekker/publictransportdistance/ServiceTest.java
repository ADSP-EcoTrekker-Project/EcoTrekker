package com.ecotrekker.publictransportdistance;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServiceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testDistanceCalculation() {
        String start = "S+U Pankow (Berlin)";
        String end = "S Schönefeld (bei Berlin) Bhf";
        String line = "S85";
        long startTime = System.currentTimeMillis();
        webTestClient.post().uri("/v1/calc/distance")
        .bodyValue(new DistanceRequest(new RouteStep(start,end,"sbahn",line, null)))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(DistanceResponse.class)
        .consumeWith(body -> { 
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Result: "+body.getResponseBody().getDistance()+"m took: "+elapsedTime+ "ms");
            assertTrue(body.getResponseBody().getDistance() == 35.39862429197586);
        });
    }
    
    @Test
    public void testDistanceCalculation2() {
        String start = "S Lankwitz (Berlin)";
        String end = "S Lichtenrade (Berlin)";
        String line = "S2B";
        long startTime = System.currentTimeMillis();
        webTestClient.post().uri("/v1/calc/distance")
        .bodyValue(new DistanceRequest(new RouteStep(start,end,"sbahn",line, null)))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(DistanceResponse.class)
        .consumeWith(body -> { 
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Result: "+body.getResponseBody().getDistance()+"km took: "+elapsedTime+ "ms");
            assertTrue(body.getResponseBody().getDistance() == 2.869771777240845 );
        });
    }

    @Test
    public void testDistanceCalculation3() {
        String start = "S Köpenick (Berlin)";
        String end = "S Tiergarten (Berlin)";
        String line = "S3";
        long startTime = System.currentTimeMillis();
        webTestClient.post().uri("/v1/calc/distance")
        .bodyValue(new DistanceRequest(new RouteStep(start,end,"sbahn",line, null)))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(DistanceResponse.class)
        .consumeWith(body -> { 
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Result: "+body.getResponseBody().getDistance()+"km took: "+elapsedTime+ "ms");
            assertTrue(body.getResponseBody().getDistance() == 16.83941862107101);
        });
    }
}
