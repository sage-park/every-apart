package com.sage.everyapart.membershipservice.domain

import org.springframework.security.crypto.password.PasswordEncoder

class Membership(
    val membershipId: MembershipId,
    val password:String,
    val name:String? = null,
    val address:String? = null,
    val email:String? = null,
    val isValid:Boolean,
    var refreshToken: String? = null
) {
    fun changeRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun authenticate(passwordEncoder: PasswordEncoder, password: String): Boolean {
        return passwordEncoder.matches(password, this.password)
    }

}
