package com.github.mzr.coder.orchestrator.kotlin.reactive.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
@ConfigurationProperties("application")
class UrlConfig {
    lateinit var decisionService: String
    lateinit var service1: String
    lateinit var service2: String
    lateinit var service3: String

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }
}