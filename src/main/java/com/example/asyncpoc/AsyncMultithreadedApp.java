package com.example.asyncpoc;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncMultithreadedApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AsyncMultithreadedApp.class, args);
        ConsumerThread consumerThread = context.getBean(ConsumerThread.class);
        consumerThread.startConsuming();
    }
}
