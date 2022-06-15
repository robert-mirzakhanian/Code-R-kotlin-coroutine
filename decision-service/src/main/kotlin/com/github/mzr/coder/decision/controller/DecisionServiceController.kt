package com.github.mzr.coder.decision.controller

import com.github.mzr.coder.model.ServiceResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.apache.logging.slf4j.MDCContextMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.coroutineContext

@RestController
class DecisionServiceController {

    val log: Logger = LoggerFactory.getLogger(DecisionServiceController::class.java)

    companion object {
        val listServices = listOf("service1", "service2", "service3")
    }

    @PostMapping("/decision")
    suspend fun getDecision(@RequestBody services: List<ServiceResponse>): ServiceResponse = withContext(MDCContext()) {
            log.info("Got request decision with list services {}", services)
            delay(3000)
            val decisionFromAllServices = services.map { it.approve }.fold(true) { acc, approve -> acc && approve }
            val response =
                ServiceResponse.builder()
                    .approve(decisionFromAllServices)
                    .build()
            log.info("Send response with approve {}", response.approve)
            response
    }

    @GetMapping("/strategy")
    suspend fun getStrategy(): List<String> = withContext(MDCContext()) {
        log.info("Got request strategy")
        delay(3000)
        log.info("Send response with services {}", listServices)
        listServices
    }
}