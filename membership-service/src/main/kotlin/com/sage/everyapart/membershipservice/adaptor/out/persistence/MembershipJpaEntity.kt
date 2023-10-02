package com.sage.everyapart.membershipservice.adaptor.out.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "membership")
class MembershipJpaEntity(
    membershipId: String,
    name:String? = null,
    address:String? = null,
    email:String? = null,
    isValid:Boolean,
    refreshToken:String? = null,
    password: String
) {

    @Id
    var membershipId: String = membershipId

    var password: String = password

    var name: String? = name

    var address:String? = address

    var email:String? = email

    var isValid:Boolean = isValid

    var refreshToken:String? = refreshToken

}
