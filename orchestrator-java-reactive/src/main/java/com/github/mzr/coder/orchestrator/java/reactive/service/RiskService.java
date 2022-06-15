package com.github.mzr.coder.orchestrator.java.reactive.service;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;
import reactor.core.publisher.Mono;

public interface RiskService {
    String getName();
    Mono<ServiceResponse> execute(ServiceRequest request);
}
