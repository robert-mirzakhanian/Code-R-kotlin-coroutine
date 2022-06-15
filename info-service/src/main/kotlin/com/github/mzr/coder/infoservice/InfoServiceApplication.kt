package com.github.mzr.coder.infoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InfoServiceApplication

fun main(args: Array<String>) {
    runApplication<InfoServiceApplication>(*args)
}
