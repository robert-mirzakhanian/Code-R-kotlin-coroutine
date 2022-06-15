package com.github.mzr.coder.orchestrator.kotlin.reactive.service.impl

import com.github.mzr.coder.model.ServiceRequest
import com.github.mzr.coder.model.ServiceResponse
import com.github.mzr.coder.orchestrator.kotlin.reactive.config.UrlConfig
import com.github.mzr.coder.orchestrator.kotlin.reactive.service.RiskService
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RiskService1Impl(
    private val webClientBuilder: WebClient.Builder,
    private val urlConfig: UrlConfig
) : RiskService {

    private val log = LoggerFactory.getLogger(RiskService1Impl::class.java)

    override val name: String = "service1"

    override suspend fun execute(request: ServiceRequest): ServiceResponse {
        val name = request.name
        log.info("Trying get info from {} for request with name {}", this.name, name)
        val serviceResponse = webClientBuilder!!.baseUrl(urlConfig.service1)
            .build()
            .post()
            .uri("/info")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ServiceResponse::class.java)
            .awaitSingle()
        log.info("Got info from {} for request with name {} and approve {}", this.name, name, serviceResponse.approve)
        return serviceResponse

    }
}