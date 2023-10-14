package com.save.everyapart.subscription.application.port.`in`

import com.save.everyapart.subscription.application.service.SubscriptionData

interface SubscriptionQuery {
    fun findAll(): List<SubscriptionData>

}
