package com.Podzilla.analytics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public final class OpenApiConfig {
    /**
     * This method configures the OpenAPI documentation for the application.
     * It sets the title, version, and description of the API.
     *
     * @return OpenAPI object with the configured information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Podzilla Analytics API")
                        .version("1.0")
                        .description(
                                "API documentation for analytics services."));
    }
}
