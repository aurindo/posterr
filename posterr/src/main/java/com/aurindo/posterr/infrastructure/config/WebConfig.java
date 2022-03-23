package com.aurindo.posterr.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("GET", "POST", "DELETE", "HEAD")
                        .maxAge(-1)   // add maxAge
                        .allowCredentials(false);
            }
        };
    }
}
