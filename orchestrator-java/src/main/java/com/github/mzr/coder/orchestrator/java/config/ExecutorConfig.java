package com.github.mzr.coder.orchestrator.java.config;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Bean
    public Executor executor(BeanFactory beanFactory) {
        return new TraceableExecutorService(beanFactory, Executors.newFixedThreadPool(2));
    }
}
