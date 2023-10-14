package com.everyapart.batch.domain

class Subscription(
    val subscriptionId: String,
    val membershipId: String,
    val regionCode: String,
    val active: Boolean,
    val emailAddress: String
) {
}
