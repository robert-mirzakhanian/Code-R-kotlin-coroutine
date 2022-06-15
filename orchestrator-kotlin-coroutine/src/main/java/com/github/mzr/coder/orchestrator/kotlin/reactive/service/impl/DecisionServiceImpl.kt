package com.github.mzr.coder.orchestrator.kotlin.reactive.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mzr.coder.model.ServiceResponse
import com.github.mzr.coder.orchestrator.kotlin.reactive.config.UrlConfig
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.DecisionService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Service
class DecisionServiceImpl(
    private val webClientBuilder: WebClient.Builder,
    private val urlConfig: UrlConfig,
    private val objectMapper: ObjectMapper
) : DecisionService {

    private val log = LoggerFactory.getLogger(DecisionServiceImpl::class.java)

    override suspend fun getStrategy(name: String): List<String> {
        log.info("Trying get strategy for request with name {}", name)
        val services = webClientBuilder.baseUrl(urlConfig.decisionService)
            .build()
            .get()
            .uri("/strategy")
            .retrieve()
            .bodyToMono(Array<String>::class.java)
            .awaitSingle()
        log.info(
            "Got strategy for request with name {}. List services {}",
            name,
            objectMapper.writeValueAsString(services)
        )
        return services.toList()
    }

    override suspend fun getDecision(name: String, servicesResponse: List<ServiceResponse>): ServiceResponse {
        log.info(
            "Trying get decision for request with name {}",
            name
        )
        val serviceResponse = webClientBuilder.baseUrl(urlConfig.decisionService)
            .build()
            .post()
            .uri("/decision")
            .bodyValue(servicesResponse)
            .retrieve()
            .bodyToMono(ServiceResponse::class.java)
            .awaitSingle()
        log.info("Got decision for request with name {}, approve {}", name, serviceResponse.approve)
        return serviceResponse
    }
}