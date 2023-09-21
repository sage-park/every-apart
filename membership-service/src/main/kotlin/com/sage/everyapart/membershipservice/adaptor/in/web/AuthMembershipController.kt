package com.sage.everyapart.membershipservice.adaptor.`in`.web

import com.sage.everyapart.membershipservice.application.port.`in`.AuthMembershipUsecase
import com.sage.everyapart.membershipservice.application.port.`in`.RefreshTokenCommand
import com.sage.everyapart.membershipservice.application.service.LoginMembershipCommand
import com.sage.everyapart.membershipservice.domain.JwtToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthMembershipController (
    private val authMembershipUsecase: AuthMembershipUsecase
) {

    @PostMapping("/membership/login")
    fun login(@RequestBody request: LoginMembershipRequest): JwtToken {

        val command = LoginMembershipCommand(
            membershipId = request.membershipId,
            password = request.password
        )
        return authMembershipUsecase.loginMembership(command)
    }

    @PostMapping("/membership/refresh-token")
    fun refreshToken(@RequestBody request: RefreshTokenRequest):JwtToken {

        val command = RefreshTokenCommand(refreshToken = request.refreshToken)

        return authMembershipUsecase.refreshJwtTokenByRefreshToken(command)
    }

    @PostMapping("/membership/token-validate")
    fun validateToken(@RequestBody request:ValidateTokenRequest):Boolean {

        val command = ValidateTokenCommand(
            jwtToken = request.jwtToken
        )

        return authMembershipUsecase.validateJwtToken(command)
    }



}
