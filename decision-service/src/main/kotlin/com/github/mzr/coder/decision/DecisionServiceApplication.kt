package com.github.mzr.coder.decision

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DecisionServiceApplication

fun main(args: Array<String>) {
    runApplication<DecisionServiceApplication>(*args)
}
