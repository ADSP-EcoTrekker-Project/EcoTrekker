package com.ecotrekker.restapi;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import com.ecotrekker.restapi.producer.EcotrekkerProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import com.ecotrekker.restapi.model.Route;
import com.ecotrekker.restapi.model.RouteResult;
import com.ecotrekker.restapi.model.RouteStep;
import com.ecotrekker.restapi.model.Routes;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.UUID;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:8080", "port=8080" })
class EmbeddedKafkaIntegrationTest {

    @Value("${topic.name}")
    String orderTopic;

    @Autowired
    private EcotrekkerProducer producer;

    @Autowired
    private TestConsumer consumer;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private final CountDownLatch waiter = new CountDownLatch(1);
    @Test
    public void simpleEmbeddedKafkaProducerTest() 
      throws Exception {
        waiter.await(1, TimeUnit.SECONDS); //Give embedded Kafka some time to launch, test becomes more flaky without
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
        HashMap<UUID, RequestReplyFuture<String, String, String>> sendResults = producer.sendMessages(routes);
        CompletableFuture<Void> resultFuture = CompletableFuture.allOf(sendResults.values().toArray(new CompletableFuture[0]));
        
        try {
            resultFuture.get(1, TimeUnit.SECONDS);
            for(RequestReplyFuture<String, String, String> requestReply: sendResults.values()) {
                RouteResult result = objectMapper.readValue(requestReply.get().value(), RouteResult.class);
                assertNotNull(result);
                assert(result.getCo2() == 999);
            }
        } catch (Exception e) {
            fail("Exception thrown on send: "+e);
        }
    }
}