package com.fortum.codechallenge.elevators.backend;

import com.fortum.codechallenge.elevators.backend.listner.EventListener;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
public class ElevatorApplication {

    @Value("${com.fortum.codechallenge.numberOfElevators}")
    private int numberOfElevators;

    public static void main(final String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(numberOfElevators);
    }

    @Bean
    public EventBus eventBus(EventListener eventListener) {
        EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        eventBus.register(eventListener);
        return eventBus;
    }
}
