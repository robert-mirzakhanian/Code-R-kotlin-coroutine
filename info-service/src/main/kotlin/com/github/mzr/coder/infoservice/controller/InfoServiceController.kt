package com.github.mzr.coder.infoservice.controller

import com.github.mzr.coder.model.ServiceRequest
import com.github.mzr.coder.model.ServiceResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class InfoServiceController {

    val log: Logger = LoggerFactory.getLogger(InfoServiceController::class.java)

    @PostMapping("/info")
    suspend fun getDecision(@RequestBody serviceRequest: ServiceRequest): ServiceResponse = withContext(MDCContext()) {
        log.info("Got request decision for {}", serviceRequest.name)
        delay(3000)
        val response = ServiceResponse.builder().approve(Random.nextBoolean()).build()
        log.info("Send response with approve {}", response.approve)
        response
    }
}