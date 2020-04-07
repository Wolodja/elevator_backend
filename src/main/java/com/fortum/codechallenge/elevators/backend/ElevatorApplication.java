package com.fortum.codechallenge.elevators.backend;

import com.fortum.codechallenge.elevators.backend.listner.EventListener;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ElevatorApplication {

    @Value("${com.fortum.codechallenge.numberOfElevators}")
    private int numberOfElevators;

    @Value("${com.fortum.codechallenge.numberOfFloors}")
    private int numberOfFloors;

    public static void main(final String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor taskExecutor() {
        return new ThreadPoolExecutor(numberOfElevators, numberOfElevators,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(numberOfFloors));
    }

    @Bean
    public EventBus eventBus(EventListener eventListener) {
        EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
        eventBus.register(eventListener);
        return eventBus;
    }
}
