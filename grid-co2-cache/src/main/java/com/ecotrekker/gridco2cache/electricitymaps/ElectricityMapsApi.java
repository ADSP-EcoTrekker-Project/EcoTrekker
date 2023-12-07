package com.ecotrekker.gridco2cache.electricitymaps;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ElectricityMapsApi {
    private static final String API_URL = "https://api-access.electricitymaps.com/free-tier/carbon-intensity/latest?zone=DE";

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task for repeated fixed-rate execution
        scheduler.scheduleAtFixedRate(() -> {
            try {
                readCarbonIntensityFromAPI(connectToAPI());
            } catch (Exception e) {
                System.err.println("Error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }, 0, 60, TimeUnit.MINUTES);
    }

    public static HttpResponse<String> connectToAPI() throws Exception {
        String authToken = System.getenv("ELECTRICITY_MAPS_AUTH_TOKEN");
        if (authToken == null || authToken.isBlank()) {
            throw new IllegalStateException("Environment variable 'ELECTRICITY_MAPS_AUTH_TOKEN' not set");
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
                .header("auth-token", authToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Connection successful. Response received.");
            return response;
        } else {
            throw new Exception("Failed to connect. HTTP status code: " + response.statusCode());
        }
    }

    public static String readCarbonIntensityFromAPI(HttpResponse<String> response) {
        String responseBody = response.body();
        System.out.println(responseBody);
        return responseBody;
    }
}