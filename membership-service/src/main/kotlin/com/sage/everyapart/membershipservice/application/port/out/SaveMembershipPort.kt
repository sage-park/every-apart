package com.sage.everyapart.membershipservice.application.port.out

import com.sage.everyapart.membershipservice.domain.Membership

interface SaveMembershipPort {
    fun save(membership: Membership)

}
