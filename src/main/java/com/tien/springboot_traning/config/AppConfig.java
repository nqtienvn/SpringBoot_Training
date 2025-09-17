package com.tien.springboot_traning.config;

import com.tien.springboot_traning.entity.jwt.JwtKeyUntil;
import com.tien.springboot_traning.entity.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AppConfig {
    @Bean
    public JwtKeyUntil getJwtKeyUntil(JwtProperties jwtProperties) {
        return new JwtKeyUntil(jwtProperties);
    }
}
