package com.github.mzr.coder.orchestrator.java.service;


import com.github.mzr.coder.model.orhestrator.OrchestratorRequest;
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse;

public interface OrchestrationService {
     OrchestratorResponse getDecision(OrchestratorRequest request);
}
