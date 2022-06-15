package com.github.mzr.coder.orchestrator.java.reactive.service;

import com.github.mzr.coder.model.ServiceResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DecisionService {
    Mono<List<String>> getStrategy(String name);
    Mono<ServiceResponse> getDecision(String name, List<ServiceResponse> serviceResponses);
}
