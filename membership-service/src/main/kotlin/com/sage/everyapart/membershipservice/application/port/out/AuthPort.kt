package com.sage.everyapart.membershipservice.application.port.out

interface AuthPort {
    fun authenticate(userId: String, password: String): Boolean

}
