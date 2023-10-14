package com.everyapart.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class BatchServiceApplication

fun main(args: Array<String>) {
    runApplication<BatchServiceApplication>(*args)
}
