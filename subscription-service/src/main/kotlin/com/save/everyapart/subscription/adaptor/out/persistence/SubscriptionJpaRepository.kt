package com.save.everyapart.subscription.adaptor.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionJpaRepository : JpaRepository<SubscriptionJpaEntity, String> {

    fun findByActive(active: Boolean): List<SubscriptionJpaEntity>
}
