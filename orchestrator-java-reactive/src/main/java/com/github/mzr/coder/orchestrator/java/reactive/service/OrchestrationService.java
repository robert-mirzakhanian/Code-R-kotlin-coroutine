package com.github.mzr.coder.orchestrator.java.reactive.service;


import com.github.mzr.coder.model.orhestrator.OrchestratorRequest;
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse;
import reactor.core.publisher.Mono;

public interface OrchestrationService {
     Mono<OrchestratorResponse> getDecision(OrchestratorRequest request);
}
