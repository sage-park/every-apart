package com.save.everyapart.subscription.application.port.`in`

import com.save.everyapart.subscription.domain.SubscriptionId

interface RegisterSubscriptionUsecase {
    fun register(command: RegisterSubscriptionCommand): SubscriptionId
}
