package com.save.everyapart.subscription.domain

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailAddress(
    val value: String
) {

    init {
        if (!isValidEmail(value)) {
            throw IllegalArgumentException("Invalid email address")
        }
    }

    fun isValidEmail(email:String): Boolean {
        var regex = "^[A-Za-z0-9+_.-]+@(.+)$"
        var pattern: Pattern = Pattern.compile(regex)
        var matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

}
