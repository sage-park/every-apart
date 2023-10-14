package com.save.everyapart.subscription.adaptor.out.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "subscription")
class SubscriptionJpaEntity {

    @Id
    @Column(name = "subscription_id")
    var subscriptionId: String

    @Column(name = "membership_id")
    var membershipId: String

    @Column(name = "region_code")
    var regionCode: String

    @Column(name = "active")
    var active: Boolean

    @Column(name = "email_address")
    var emailAddress: String

    constructor(
        subscriptionId: String,
        membershipId: String,
        regionCode: String,
        active: Boolean,
        emailAddress: String
    ) {
        this.subscriptionId = subscriptionId
        this.membershipId = membershipId
        this.regionCode = regionCode
        this.active = active
        this.emailAddress = emailAddress
    }
}
