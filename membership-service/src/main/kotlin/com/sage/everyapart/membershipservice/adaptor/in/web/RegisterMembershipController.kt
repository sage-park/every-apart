package com.sage.everyapart.membershipservice.adaptor.`in`.web

import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipCommand
import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipUsecase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterMembershipController(
    val registerMembershipUsecase: RegisterMembershipUsecase
) {

    @PostMapping("/membership/register")
    fun register(@RequestBody registerMembershipRequest:RegisterMembershipRequest): ResponseEntity<Unit> {

        val command = RegisterMembershipCommand(
            id = registerMembershipRequest.id,
            password = registerMembershipRequest.password
        )
        registerMembershipUsecase.register(command)


        return ResponseEntity(HttpStatus.CREATED)
    }

}
