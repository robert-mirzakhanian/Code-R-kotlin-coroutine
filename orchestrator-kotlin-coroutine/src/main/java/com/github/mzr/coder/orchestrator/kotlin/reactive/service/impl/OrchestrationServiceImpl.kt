package com.github.mzr.coder.orchestrator.kotlin.reactive.service.impl

import com.github.mzr.coder.model.ServiceRequest
import com.github.mzr.coder.model.ServiceResponse
import com.github.mzr.coder.model.orhestrator.OrchestratorRequest
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.DecisionService
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.OrchestrationService
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.RiskService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Service
class OrchestrationServiceImpl(
    private val decisionService: DecisionService,
    private val riskServices: List<RiskService>
) : OrchestrationService {

    override suspend fun getDecision(request: OrchestratorRequest): OrchestratorResponse {
        val strategy = decisionService.getStrategy(request.name)
        val serviceRequest = ServiceRequest.builder().name(request.name).build()
        val servicesResponse = coroutineScope {
             riskServices.asFlow()
                 .filter { strategy.contains(it.name) }
                 .map { async { it.execute(serviceRequest) } }
                 .buffer()
                 .map { it.await() }
                 .toList()
        }
        val decision = decisionService.getDecision(request.name, servicesResponse)
        return OrchestratorResponse.builder().approve(decision.approve).build()
    }

    private fun getServicesByStrategy(strategy: List<String>): List<RiskService> {
        return riskServices.stream().filter { rs: RiskService -> strategy!!.contains(rs.name) }
            .collect(Collectors.toList())
    }
}