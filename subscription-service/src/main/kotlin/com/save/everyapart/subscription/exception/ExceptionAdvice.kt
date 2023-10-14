package com.save.everyapart.subscription.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception): ResponseEntity<String> {
        e.printStackTrace()
        return ResponseEntity.internalServerError().body(e.message)
    }

}
