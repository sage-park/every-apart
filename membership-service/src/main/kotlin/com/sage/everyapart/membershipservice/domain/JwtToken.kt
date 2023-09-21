package com.sage.everyapart.membershipservice.domain

class JwtToken(
    membershipId: MembershipId,
    jwtToken: MembershipJwtToken,
    refreshToken: MembershipRefreshToken
) {

    val membershipId:String;
    val jwtToken:String
    val refreshToken:String;

    init {
        this.membershipId = membershipId.membershipId
        this.jwtToken = jwtToken.jwtToken
        this.refreshToken = refreshToken.refreshToken
    }


}

class MembershipId(val membershipId: String)
class MembershipJwtToken(val jwtToken: String)
class MembershipRefreshToken(val refreshToken: String)
