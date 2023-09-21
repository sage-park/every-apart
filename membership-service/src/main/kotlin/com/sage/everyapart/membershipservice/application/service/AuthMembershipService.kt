package com.sage.everyapart.membershipservice.application.service

import com.sage.everyapart.membershipservice.adaptor.`in`.web.ValidateTokenCommand
import com.sage.everyapart.membershipservice.application.port.`in`.AuthMembershipUsecase
import com.sage.everyapart.membershipservice.application.port.`in`.RefreshTokenCommand
import com.sage.everyapart.membershipservice.application.port.out.AuthPort
import com.sage.everyapart.membershipservice.application.port.out.TokenPort
import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.domain.*
import com.sage.everyapart.membershipservice.exception.ErrorCode
import com.sage.everyapart.membershipservice.exception.MembershipException
import org.springframework.stereotype.Service

@Service
class AuthMembershipService(
    val tokenPort: TokenPort,
    val findMembershipPort: FindMembershipPort,
    val saveMembershipPort: SaveMembershipPort,
    val authPort : AuthPort

):AuthMembershipUsecase {

    override fun loginMembership(command: LoginMembershipCommand): JwtToken {

        val membershipId = command.membershipId

        val membership = findMembershipPort.findMembership(membershipId)

        if (!membership.isValid) {
            throw MembershipException(
                message = "invalid membersihp",
                errorCode = ErrorCode.INVALID_MEMBERSHIP)
        }

        if (!authPort.authenticate(membershipId, command.password)) {
            throw MembershipException(
                message = "invalid password",
                errorCode = ErrorCode.INVALID_PASSWORD
            )
        }

        val jwtToken = tokenPort.generateJwtToken(MembershipId(membershipId))

        val refreshToken = tokenPort.generateRefreshToken(MembershipId(membershipId))

        membership.changeRefreshToken(refreshToken)

        saveMembershipPort.save(membership)

        return JwtToken(
            membershipId = MembershipId(membershipId),
            jwtToken = MembershipJwtToken(jwtToken),
            refreshToken = MembershipRefreshToken(refreshToken)
        )

    }

    override fun refreshJwtTokenByRefreshToken(command: RefreshTokenCommand): JwtToken {

        val refreshToken = command.refreshToken

        val isValid = tokenPort.validateJwtToken(refreshToken)

        if (!isValid) throw MembershipException(
            message = "invalid token",
            errorCode = ErrorCode.INVALID_TOKEN
        )

        val membershipId = tokenPort.parseMembershipIdFromToken(refreshToken)
        val membershipIdString = membershipId.membershipId

        val membership = findMembershipPort.findMembership(membershipIdString)

        if (!membership.refreshToken.equals(refreshToken)) throw MembershipException(
            message = "등록되어있는 refresh token 과 다릅니다.",
            errorCode = ErrorCode.INVALID_TOKEN
        )

        if (!membership.isValid) throw MembershipException(
            message = "membership 이 유효한 상태가 아닙니다.",
            errorCode = ErrorCode.INVALID_MEMBERSHIP
        )

        val newJwtToken = tokenPort.generateJwtToken(membershipId)

        return JwtToken(
            membershipId,
            MembershipJwtToken(newJwtToken),
            MembershipRefreshToken(refreshToken)
        )
    }

    override fun validateJwtToken(command: ValidateTokenCommand): Boolean {

        val jwtToken = command.jwtToken

        return tokenPort.validateJwtToken(jwtToken)
    }

    override fun getMembershipByJwtToken(command: ValidateTokenCommand): Membership {

        val jwtToken = command.jwtToken

        val isValid = tokenPort.validateJwtToken(jwtToken)

        if (!isValid) throw MembershipException(
            message = "invalid token",
            errorCode = ErrorCode.INVALID_TOKEN
        )

        val membershipId = tokenPort.parseMembershipIdFromToken(jwtToken)

        return findMembershipPort.findMembership(membershipId.membershipId)
    }
}
