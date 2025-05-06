package com.Podzilla.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Podzilla analytics system.
 */
@SpringBootApplication
public final class AnalyticsApplication {

    private AnalyticsApplication() {
        // Prevent instantiation
    }

    /**
     * Main entry point for the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}
