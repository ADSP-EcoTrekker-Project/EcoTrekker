package com.ecotrekker.co2calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.ecotrekker.co2calculator.model.Route;
import com.ecotrekker.co2calculator.model.RouteResult;
import com.ecotrekker.co2calculator.model.RouteStep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalculatorApplicationTests {
    
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

	@Test
	void contextLoads() {
	}

    @Test
    void testResponse() {
        Route testRoute = new Route();
        LinkedList<RouteStep> testSteps = new LinkedList<>();
        RouteStep step1 = new RouteStep();
        RouteStep step2 = new RouteStep();
        testSteps.add(step1);
        testSteps.add(step2);
        testRoute.setSteps(testSteps); 
        
        ResponseEntity<RouteResult> result = testRestTemplate.exchange(
            "http://localhost:"+port+"/v1/calc/co2", 
            HttpMethod.POST, 
            new HttpEntity<Route>(testRoute), 
            RouteResult.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(result.getBody().getCo2(), 999d);
        
    }
}
