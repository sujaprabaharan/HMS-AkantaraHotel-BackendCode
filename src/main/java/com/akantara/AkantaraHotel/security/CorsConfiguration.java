package com.akantara.AkantaraHotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//  The CorsConfiguration class is used to configure global CORS settings for a Spring Boot application.
//  It allows frontend applications running on different domains to access the backend APIs safely, and it specifies which HTTP methods and origins are allowed.
@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            // Override method addCorsMapping from WebMvcConfigurer interface to customize CORS mappings
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*");
            }
        };
    }

}
