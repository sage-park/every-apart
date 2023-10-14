package com.everyapart.batch.adaptor.out.service

data class SubscriptionData(
    val subscriptionId: String,
    val membershipId: String,
    val regionCode: String,
    val active: Boolean,
    val emailAddress: String
) {
}
