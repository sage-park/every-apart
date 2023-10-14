package com.save.everyapart.subscription.application.service

import com.save.everyapart.subscription.application.port.`in`.SubscriptionQuery
import com.save.everyapart.subscription.application.port.out.SubscriptionPort
import org.springframework.stereotype.Service

@Service
class SearchSubscriptionService(
    val subscriptionPort: SubscriptionPort
) : SubscriptionQuery{


    override fun findAll(): List<SubscriptionData> {
        return subscriptionPort.findAll()
    }
}
