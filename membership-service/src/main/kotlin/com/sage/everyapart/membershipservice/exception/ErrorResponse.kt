package com.sage.everyapart.membershipservice.exception

data class ErrorResponse (
    val errorCode: ErrorCode,
    val message: String
)
