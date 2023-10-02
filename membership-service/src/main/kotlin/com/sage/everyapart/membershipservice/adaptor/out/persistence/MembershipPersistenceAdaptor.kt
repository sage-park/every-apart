package com.sage.everyapart.membershipservice.adaptor.out.persistence

import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId
import com.sage.everyapart.membershipservice.exception.ErrorCode
import com.sage.everyapart.membershipservice.exception.MembershipException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional
class MembershipPersistenceAdaptor(
    private val membershipJpaRepository: MembershipJpaRepository
):FindMembershipPort, SaveMembershipPort {

    override fun findMembership(membershipId: String): Membership {

        val nullableEntity = membershipJpaRepository.findById(membershipId)

        if (nullableEntity.isPresent) {
            val entity = nullableEntity.get()

            return Membership(
                membershipId = MembershipId(entity.membershipId),
                name = entity.name,
                address = entity.address,
                email = entity.email,
                isValid = entity.isValid,
                refreshToken = entity.refreshToken,
                password = entity.password
            )

        } else {
            throw MembershipException(
                message = "membership 이 없습니다.",
                errorCode = ErrorCode.MEMBERSHIP_NOTFOUND
            )

        }

    }

    override fun exist(membershipId: MembershipId): Boolean {
        return membershipJpaRepository.existsById(membershipId.membershipId)
    }

    override fun save(membership: Membership) {

        val nullableEntity = membershipJpaRepository.findById(membership.membershipId.membershipId)

        if (!nullableEntity.isPresent) {

            //insert
            val membershipJpaEntity = MembershipJpaEntity(
                membershipId = membership.membershipId.membershipId,
                name = membership.name,
                address = membership.address,
                email = membership.email,
                isValid = membership.isValid,
                refreshToken = membership.refreshToken,
                password = membership.password
            )

            membershipJpaRepository.save(membershipJpaEntity)

        } else {

            //update
            val entity = nullableEntity.get()

            entity.name = membership.name
            entity.address = membership.address
            entity.email = membership.email
            entity.isValid = membership.isValid
            entity.refreshToken = membership.refreshToken
            entity.password = membership.password

        }

    }


}
