package com.ecotrekker.restapi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

@SpringBootApplication
@EnableFeignClients
@Async
@Configuration
public class EcotrekkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcotrekkerApplication.class, args);
	}

	@Bean
    public TaskExecutor taskExecutor(){
		ExecutorService execService = Executors.newCachedThreadPool();
		ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor(execService);
        return executor;
    }

}
