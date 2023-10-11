package com.sage.everyapart.membershipservice.adaptor.`in`.web

data class MembershipData (
    val membershipId:String,
    val name:String? = "",
    val address:String? = "",
    val email:String? = "",
    val isValid:Boolean,
) {
}
