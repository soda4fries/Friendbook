package com.wia1002g3.friendbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081") // Add the origin of your frontend application
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the HTTP methods you want to allow
                        .allowedHeaders("*") // Add the headers you want to allow
                        .allowCredentials(true); // Allow sending cookies across origins if needed
            }
        };
    }
}
