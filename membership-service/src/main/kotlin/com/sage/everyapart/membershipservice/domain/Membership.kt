package com.sage.everyapart.membershipservice.domain

class Membership(
    val membershipId: MembershipId,
    val password:String,
    val name:String,
    val address:String,
    val email:String,
    val isValid:Boolean,
    val isCorp:Boolean,
    var refreshToken: String?
) {
    fun changeRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

}
