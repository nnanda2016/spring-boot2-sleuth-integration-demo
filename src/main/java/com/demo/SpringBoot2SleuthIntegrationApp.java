package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;

/**
 * TODO: Add a description
 * 
 * @author Niranjan Nanda
 */
@SpringBootApplication
public class SpringBoot2SleuthIntegrationApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2SleuthIntegrationApp.class, args);
    }
    
    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder()
                .currentTraceContext(ThreadContextCurrentTraceContext.create())
                .build();
    }
}
