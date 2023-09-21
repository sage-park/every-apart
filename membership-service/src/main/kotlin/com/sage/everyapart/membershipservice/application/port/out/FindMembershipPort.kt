package com.sage.everyapart.membershipservice.application.port.out

import com.sage.everyapart.membershipservice.domain.Membership

interface FindMembershipPort {
    fun findMembership(membershipId: String): Membership

}
