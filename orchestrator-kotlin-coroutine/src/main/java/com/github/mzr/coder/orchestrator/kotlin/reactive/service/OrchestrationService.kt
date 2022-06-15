package com.github.mzr.coder.orchestrator.kotlin.reactive.service

import com.github.mzr.coder.model.orhestrator.OrchestratorRequest
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse
import reactor.core.publisher.Mono

interface OrchestrationService {
    suspend fun getDecision(request: OrchestratorRequest): OrchestratorResponse
}