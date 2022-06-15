package com.github.mzr.coder.orchestrator.kotlin.reactive.service

import com.github.mzr.coder.model.ServiceRequest
import com.github.mzr.coder.model.ServiceResponse
import reactor.core.publisher.Mono

interface RiskService {
    val name: String
    suspend fun execute(request: ServiceRequest): ServiceResponse
}