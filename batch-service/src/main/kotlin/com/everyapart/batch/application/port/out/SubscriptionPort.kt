package com.everyapart.batch.application.port.out

import com.everyapart.batch.domain.Subscription

interface SubscriptionPort {
    fun getAll(): List<Subscription>

}
