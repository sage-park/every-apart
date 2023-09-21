package com.sage.everyapart.membershipservice.adaptor.out.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "membership")
class MembershipJpaEntity(
    membershipId: Long,
    name:String,
    address:String,
    email:String,
    isValid:Boolean,
    isCorp:Boolean,
    refreshToken:String?,
    password: String
) {

    @Id
    @GeneratedValue
    var membershipId: Long = membershipId

    var password: String = password

    var name: String = name

    var address:String = address

    var email:String = email

    var isValid:Boolean = isValid

    var isCorp:Boolean = isCorp

    var refreshToken:String? = refreshToken

}
