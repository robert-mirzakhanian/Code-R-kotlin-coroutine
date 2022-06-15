package com.github.mzr.coder.orchestrator.kotlin.reactive

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class OrchestratorKotlinReactiveApplication

fun main(args: Array<String>) {
    SpringApplication.run(OrchestratorKotlinReactiveApplication::class.java, *args)
}
