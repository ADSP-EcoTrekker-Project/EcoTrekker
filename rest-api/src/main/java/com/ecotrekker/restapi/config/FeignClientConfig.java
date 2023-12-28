package com.ecotrekker.restapi.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class FeignClientConfig {

    @Value("${client.timeout}")
    private int timeout;

    @Bean
    public OkHttpClient client(){
        OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .build();
        return client;
    }


}
