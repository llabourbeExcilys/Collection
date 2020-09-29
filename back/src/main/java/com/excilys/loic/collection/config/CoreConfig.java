package com.excilys.loic.collection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CoreConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/author").allowedOrigins("http://localhost:8080");
                registry.addMapping("/book").allowedOrigins("http://localhost:8080");
                registry.addMapping("/editor").allowedOrigins("http://localhost:8080");
                registry.addMapping("/genre").allowedOrigins("http://localhost:8080");
                registry.addMapping("/serie").allowedOrigins("http://localhost:8080");
            }
        };
    }

}
