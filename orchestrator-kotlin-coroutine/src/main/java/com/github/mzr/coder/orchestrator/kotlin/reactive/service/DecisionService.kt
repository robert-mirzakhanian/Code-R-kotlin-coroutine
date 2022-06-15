package com.github.mzr.coder.orchestrator.kotlin.reactive.service

import com.github.mzr.coder.model.ServiceResponse
import reactor.core.publisher.Mono

interface DecisionService {
    suspend fun getStrategy(name: String): List<String>
    suspend fun getDecision(name: String, serviceResponses: List<ServiceResponse>): ServiceResponse
}