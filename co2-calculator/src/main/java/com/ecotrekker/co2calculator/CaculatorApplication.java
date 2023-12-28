package com.ecotrekker.co2calculator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
@Configuration
public class CaculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaculatorApplication.class, args);
	}

	@Bean
    public TaskExecutor taskExecutor(){
		ExecutorService execService = Executors.newCachedThreadPool();
		ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor(execService);
        return executor;
    }
}
