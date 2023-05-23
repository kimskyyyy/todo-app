package com.example.todo_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    private final long MAX_AGE_SECS = 3600;
    private final long MAX_AGE_SECS = 5200;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Origin이 http:localhost:3000에 대해
                .allowedOrigins("http://localhost:3000")
                // GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                // 모든 헤더 허용
                .allowedHeaders("*")
                // 인증에 관한 정보 허용
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
