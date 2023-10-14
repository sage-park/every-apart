package com.save.everyapart.subscription.adaptor.`in`.web

data class PostSubscriptionsRequest(
    val token: String,
    val regionCode: String,
    val mailAddress: String
) {

}
