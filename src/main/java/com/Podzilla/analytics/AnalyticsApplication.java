package com.Podzilla.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "com.podzilla", "com.Podzilla" })
@EnableScheduling
public class AnalyticsApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}
