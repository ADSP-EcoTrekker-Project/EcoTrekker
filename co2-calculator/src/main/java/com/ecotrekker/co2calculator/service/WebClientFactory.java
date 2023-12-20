package com.ecotrekker.co2calculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientFactory {

    @Value("${consumption-service.address}")
    private String consumURL;

    @Value("${calculation-service.timeout}")
    private int requestTimeout;

    private HttpClient makeHttpClient(){
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, requestTimeout)
            .responseTimeout(Duration.ofMillis(requestTimeout))
            .doOnConnected(conn -> 
                conn.addHandlerLast(new ReadTimeoutHandler(requestTimeout, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(requestTimeout, TimeUnit.MILLISECONDS)));
        
        return httpClient;
    }

    public WebClient makeWebClient(){
        HttpClient httpClient = makeHttpClient();

        WebClient client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        return client;
    }

    public WebClient makeWebClient(String baseURL){
        HttpClient httpClient = makeHttpClient();

        WebClient client = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl(baseURL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        return client;
    }

    @Bean
    public WebClient makeConsumptionClient(){
        return makeWebClient(consumURL);
    }

}
