package com.everyapart.batch.application.port.out

import com.everyapart.batch.domain.ApartmentTransaction

interface MessageBrokerPort {

    fun send(message: String)
    fun send(message: List<ApartmentTransaction>)
}
