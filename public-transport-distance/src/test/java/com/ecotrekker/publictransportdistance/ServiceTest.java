package com.ecotrekker.publictransportdistance;

import com.ecotrekker.publictransportdistance.model.DistanceRequest;
import com.ecotrekker.publictransportdistance.model.DistanceResponse;
import com.ecotrekker.publictransportdistance.model.RouteStep;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            assertTrue(body.getResponseBody().getDistance() == 0.18906660318538865 + 0.4334266938652022);
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
            assertTrue(body.getResponseBody().getDistance() == 0.33385591527773206 + 0.3405406025374646);
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
            assertTrue(body.getResponseBody().getDistance() == 0.33385591527773206 + 0.3405406025374646);
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

    @Test
    public void testPerformance() {
        String start = "S+U Pankow (Berlin)";
        String end = "S Schönefeld (bei Berlin) Bhf";
        String line = "S85";
        int numRequests = 10000;
        for (int i = 0; i < numRequests; i++) {
            webTestClient.post().uri("/v1/calc/distance")
                    .bodyValue(new DistanceRequest(new RouteStep(start, end, "sbahn", line, null)))
                    .exchange()
                    .expectStatus().is2xxSuccessful();
        }
    }

    @Test
    public void testConcurrency() {
        String start = "S+U Pankow (Berlin)";
        String end = "S Schönefeld (bei Berlin) Bhf";
        String line = "S85";
        int numThreads = 10;
        int numRequestsPerThread = 1000;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < numRequestsPerThread; j++) {
                    webTestClient.post().uri("/v1/calc/distance")
                            .bodyValue(new DistanceRequest(new RouteStep(start, end, "sbahn", line, null)))
                            .exchange()
                            .expectStatus().is2xxSuccessful();
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executorService.shutdown();
    }

}
