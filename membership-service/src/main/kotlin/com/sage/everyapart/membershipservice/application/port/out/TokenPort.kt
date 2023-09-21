package com.sage.everyapart.membershipservice.application.port.out

import com.sage.everyapart.membershipservice.domain.MembershipId

interface TokenPort {

    // membership id 를 기준으로, jwt token 을 생성한다.
    fun generateJwtToken(membershipId: MembershipId):String

    // membership id 를 기준으로, refresh token 을 생성한다.
    fun generateRefreshToken(membershipId: MembershipId):String

    // jwtToken 이 유용한지 확인
    fun validateJwtToken(jwtToken:String):Boolean

    // jwtToken 으로, 어떤 멤버십 id 를 가지는지 확인
    fun parseMembershipIdFromToken(jwtToken: String):MembershipId

}
