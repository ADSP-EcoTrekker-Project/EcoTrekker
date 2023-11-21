package com.ecotrekker.restapi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.ecotrekker.restapi.producer.EcotrekkerProducer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteStep;
import com.ecotrekker.restapi.model.Routes;
import org.springframework.kafka.support.SendResult;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.List;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:8080", "port=8080" })
class EmbeddedKafkaIntegrationTest {

    @Autowired
    private EcotrekkerProducer producer;

    @Test
    public void simpleEmbeddedKafkaProducerTest() 
      throws Exception {
        Routes routes = new Routes();
        LinkedList<Route> testRoutes = new LinkedList<>();
        Route testRoute = new Route();
        LinkedList<RouteStep> testSteps = new LinkedList<>();
        RouteStep step1 = new RouteStep();
        RouteStep step2 = new RouteStep();
        testSteps.add(step1);
        testSteps.add(step2);
        testRoute.setSteps(testSteps);
        testRoutes.add(testRoute);
        routes.setRoutes(testRoutes);
        List<CompletableFuture<SendResult<String, String>>> sendResults = producer.sendMessages(routes);
      
        CompletableFuture<Void> resultFuture = CompletableFuture.allOf(sendResults.toArray(new CompletableFuture[0]));
        
        try {
            resultFuture.get(1000, TimeUnit.MILLISECONDS);
            for(CompletableFuture<SendResult<String, String>> sendResult: sendResults) {
                assertNotNull(sendResult.get());
            }
        } catch (Exception e) {
            fail("Exception thrown on send: "+e);
        }
    }
}