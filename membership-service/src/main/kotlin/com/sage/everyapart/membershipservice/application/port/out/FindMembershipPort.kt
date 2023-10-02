package com.sage.everyapart.membershipservice.application.port.out

import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId

interface FindMembershipPort {
    fun findMembership(membershipId: String): Membership
    fun exist(membershipId: MembershipId): Boolean

}
