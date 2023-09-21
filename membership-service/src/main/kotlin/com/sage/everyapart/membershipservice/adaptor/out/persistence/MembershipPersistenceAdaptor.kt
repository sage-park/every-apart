package com.sage.everyapart.membershipservice.adaptor.out.persistence

import com.sage.everyapart.membershipservice.application.port.out.AuthPort
import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId
import com.sage.everyapart.membershipservice.exception.ErrorCode
import com.sage.everyapart.membershipservice.exception.MembershipException
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
@Transactional
class MembershipPersistenceAdaptor(
    private val membershipJpaRepository: MembershipJpaRepository,
    private val passwordEncoder: PasswordEncoder
):FindMembershipPort, SaveMembershipPort, AuthPort {

    override fun findMembership(membershipId: String): Membership {

        val nullableEntity = membershipJpaRepository.findById(membershipId.toLong())

        if (nullableEntity.isPresent) {
            val entity = nullableEntity.get()

            return Membership(
                membershipId = MembershipId(entity.membershipId.toString()),
                name = entity.name,
                address = entity.address,
                email = entity.email,
                isValid = entity.isValid,
                isCorp = entity.isCorp,
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

    override fun save(membership: Membership) {

        val nullableEntity = membershipJpaRepository.findById(membership.membershipId.membershipId.toLong())

        if (!nullableEntity.isPresent) {

            //insert
            val membershipJpaEntity = MembershipJpaEntity(
                membershipId = membership.membershipId.membershipId.toLong(),
                name = membership.name,
                address = membership.address,
                email = membership.email,
                isValid = membership.isValid,
                isCorp = membership.isCorp,
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
            entity.isCorp = membership.isCorp
            entity.refreshToken = membership.refreshToken
            entity.password = membership.password

        }

    }

    override fun authenticate(userId: String, password: String): Boolean {

        val membership = findMembership(userId)

        return passwordEncoder.matches(password, membership.password)
    }


}
