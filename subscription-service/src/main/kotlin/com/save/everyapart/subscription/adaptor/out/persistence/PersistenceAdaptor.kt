package com.save.everyapart.subscription.adaptor.out.persistence

import com.save.everyapart.subscription.application.port.out.SubscriptionPort
import com.save.everyapart.subscription.application.service.SubscriptionData
import com.save.everyapart.subscription.domain.Subscription
import org.springframework.stereotype.Repository

@Repository
class PersistenceAdaptor(
    val subscriptionJpaRepository: SubscriptionJpaRepository
) : SubscriptionPort{
    override fun save(subscription: Subscription) {

        val findResult = subscriptionJpaRepository.findById(subscription.subscriptionId.value)

        if (findResult.isPresent) {

            val oldSubscription = findResult.get()
            oldSubscription.active = subscription.active

        } else {

            subscriptionJpaRepository.save(SubscriptionJpaEntity(
                subscriptionId = subscription.subscriptionId.value,
                membershipId = subscription.membershipId.membershipId,
                regionCode = subscription.regionCode.regionCode,
                active = subscription.active,
                emailAddress = subscription.emailAddress.value
            ))

        }

    }

    override fun findAll(): List<SubscriptionData> {

        val all = subscriptionJpaRepository.findByActive(true)

        return all.stream()
            .map { SubscriptionData(
                subscriptionId = it.subscriptionId,
                membershipId = it.membershipId,
                regionCode = it.regionCode,
                active = it.active,
                emailAddress = it.emailAddress
            ) }
            .toList()

    }
}
