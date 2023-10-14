package com.save.everyapart.subscription.domain

class Subscription(
    val subscriptionId: SubscriptionId,
    val membershipId: MembershipId,
    val regionCode: RegionCode,
    val active: Boolean,
    val emailAddress: EmailAddress
) {

}
