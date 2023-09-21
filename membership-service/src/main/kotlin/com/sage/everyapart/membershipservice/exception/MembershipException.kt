package com.sage.everyapart.membershipservice.exception

class MembershipException(
    message: String?,
    cause: Throwable? = null,
    val errorCode: ErrorCode
): RuntimeException(message, cause) {
}

