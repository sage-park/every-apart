package com.sage.everyapart.membershipservice.adaptor.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository

interface MembershipJpaRepository : JpaRepository<MembershipJpaEntity, Long> {
}
