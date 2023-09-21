package com.sage.everyapart.membershipservice.application.port.`in`

import com.sage.everyapart.membershipservice.adaptor.`in`.web.ValidateTokenCommand
import com.sage.everyapart.membershipservice.application.service.LoginMembershipCommand
import com.sage.everyapart.membershipservice.domain.JwtToken
import com.sage.everyapart.membershipservice.domain.Membership

interface AuthMembershipUsecase {
    fun loginMembership(command: LoginMembershipCommand): JwtToken
    fun refreshJwtTokenByRefreshToken(command: RefreshTokenCommand): JwtToken
    fun validateJwtToken(command: ValidateTokenCommand): Boolean

    fun getMembershipByJwtToken(command: ValidateTokenCommand): Membership

}
