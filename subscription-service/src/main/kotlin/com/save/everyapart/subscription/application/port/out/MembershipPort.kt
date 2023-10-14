package com.save.everyapart.subscription.application.port.out

import com.save.everyapart.subscription.domain.JwtToken
import com.save.everyapart.subscription.domain.Membership

interface MembershipPort {
    fun isValid(token: JwtToken): Boolean
    fun getMembershipInfo(token: JwtToken): Membership

}
