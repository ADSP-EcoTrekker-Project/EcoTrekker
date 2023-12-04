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
    private static final String AUTH_TOKEN = "yi9wa2inSOpmmrfLJGJoQRbbdtf2aF1B";

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
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
                .header("auth-token", AUTH_TOKEN)
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
        //sendToKafka(responseBody);
        return responseBody;
    }

    public static void sendToKafka(String carbonIntensityResponse) {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            try (Producer<String, String> producer = new KafkaProducer<>(props)) {
                ProducerRecord<String, String> record = new ProducerRecord<>("carbon-intensity", carbonIntensityResponse);
                producer.send(record);
                System.out.println("Record sent to Kafka successfully");
            } catch (Exception e) {
                System.err.println("Error in sending record to Kafka");
                e.printStackTrace();
            }
    }
}