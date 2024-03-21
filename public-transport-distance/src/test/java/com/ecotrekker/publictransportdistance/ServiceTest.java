package com.ecotrekker.publictransportdistance;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import com.ecotrekker.publictransportdistance.service.GenericCacheManager;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServiceTest {
    @Autowired
    private GenericCacheManager<RouteStep,Double> genericCacheManager;
    
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        genericCacheManager.reset();
    }

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
            assertTrue(body.getResponseBody().getDistance() == 35.39862429197586 * 1000);
        });
    }

    @Test
    public void testDistanceCalculationCalculationCache() {
        String start = "S+U Pankow (Berlin)";
        String end = "S Schönefeld (bei Berlin) Bhf";
        String line = "S85";
        RouteStep testRouteStep = new RouteStep(start, end, end, line, null);
        long startTime = System.currentTimeMillis();
        DistanceResponse reply = webTestClient.post().uri("/v1/calc/distance")
            .bodyValue(new DistanceRequest(testRouteStep))
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .expectBody(DistanceResponse.class)
            .returnResult()
            .getResponseBody();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Result: "+reply.getDistance()+"m took: "+elapsedTime+ "ms");
        assertTrue(reply.getDistance() == 35.39862429197586);
        assertTrue(genericCacheManager.get(testRouteStep) == 35.39862429197586);
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
            assertTrue(body.getResponseBody().getDistance() == 2.869771777240845 * 1000);
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
            assertTrue(body.getResponseBody().getDistance() == 16.83941862107101 * 1000);
        });
    }

    @Test
    public void testDistanceCalculation4() {
        String start = "Lagunenweg (Berlin)";
        String end = "Frankenbergstr. (Berlin)";
        String line = "161";
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
            assertTrue(body.getResponseBody().getDistance() == (0.18906660318538865 + 0.4334266938652022) * 1000);
        });
    }

    @Test
    public void testDistanceCalculation5() {
        String start = "Frankenbergstr. (Berlin)";
        String end = "Lagunenweg (Berlin)";
        String line = "161";
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
            assertTrue(body.getResponseBody().getDistance() == (0.33385591527773206 + 0.3405406025374646) * 1000);
        });
    }

    @Test
    public void testDistanceCalculation6() {
        String start = "Frankenbergstr. (Berlin)";
        String end = "Fürstenwalder Allee/Schule (Berlin)";
        String line = "161";
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
            assertTrue(body.getResponseBody().getDistance() == (0.33385591527773206 + 0.3405406025374646) * 1000);
        });
    }

    @Test
    public void testInvalidInput() {
        String start = null;
        String end = "";
        String line = "S2";
        webTestClient.post().uri("/v1/calc/distance")
                .bodyValue(new DistanceRequest(new RouteStep(start, end, "sbahn", line, null)))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void testNonexistentStation() {
        String start = "Nonexistent Station";
        String end = "Invalid End Station";
        String line = "S2";
        webTestClient.post().uri("/v1/calc/distance")
                .bodyValue(new DistanceRequest(new RouteStep(start, end, "sbahn", line, null)))
                .exchange()
                .expectStatus().is4xxClientError();
    }
}
