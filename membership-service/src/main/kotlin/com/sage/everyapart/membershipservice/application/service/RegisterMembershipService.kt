package com.sage.everyapart.membershipservice.application.service

import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipCommand
import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipUsecase
import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId
import com.sage.everyapart.membershipservice.exception.MembershipAlreadyExistException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterMembershipService(
    val findMembershipPort: FindMembershipPort,
    val saveMembershipPort: SaveMembershipPort,
    val passwordEncoder: PasswordEncoder
): RegisterMembershipUsecase {
    override fun register(command: RegisterMembershipCommand) {

        val exist = findMembershipPort.exist(MembershipId(command.id))

        if (exist) {
            throw MembershipAlreadyExistException()
        }

        val membership = Membership(
            membershipId = MembershipId(command.id),
            password = passwordEncoder.encode(command.password),
            isValid = true
        )
        saveMembershipPort.save(membership)

    }

}
