package com.github.mzr.coder.orchestrator.kotlin.reactive.controller

import com.github.mzr.coder.model.orhestrator.OrchestratorRequest
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.OrchestrationService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class OrchestrationController(
    private val service: OrchestrationService
) {

    val log: Logger = LoggerFactory.getLogger(OrchestrationController::class.java)

    @PostMapping("/decision")
    suspend fun getDecision(@RequestBody orchestratorRequest: OrchestratorRequest): OrchestratorResponse {
        log.info("Get request with name {} for decision", orchestratorRequest.name)
        val orchestratorResponse = service.getDecision(orchestratorRequest)
        log.info("Got decision for name {}, decision {}", orchestratorRequest.name, orchestratorResponse.approve)
        return orchestratorResponse
    }
}