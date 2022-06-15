package com.github.mzr.coder.orchestrator.java.reactive.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

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
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
