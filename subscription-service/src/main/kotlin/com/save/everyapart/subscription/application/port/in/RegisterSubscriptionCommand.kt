package com.save.everyapart.subscription.application.port.`in`

import com.save.everyapart.subscription.domain.EmailAddress
import com.save.everyapart.subscription.domain.JwtToken
import com.save.everyapart.subscription.domain.RegionCode

data class RegisterSubscriptionCommand(
    val token: JwtToken,
    val regionCode: RegionCode,
    val emailAddress: EmailAddress
) {

}
