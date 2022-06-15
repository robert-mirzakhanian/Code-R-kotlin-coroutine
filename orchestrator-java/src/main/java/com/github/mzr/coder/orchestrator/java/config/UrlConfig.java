package com.github.mzr.coder.orchestrator.java.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Setter
@Getter
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("application")
public class UrlConfig {
    private String decisionService;
    private String service1;
    private String service2;
    private String service3;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
