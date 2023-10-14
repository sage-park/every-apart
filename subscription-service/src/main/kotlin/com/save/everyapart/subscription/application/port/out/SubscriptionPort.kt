package com.save.everyapart.subscription.application.port.out

import com.save.everyapart.subscription.application.service.SubscriptionData
import com.save.everyapart.subscription.domain.Subscription

interface SubscriptionPort {
    fun save(subscription: Subscription)
    fun findAll(): List<SubscriptionData>

}
