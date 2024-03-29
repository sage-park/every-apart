package com.save.everyapart.subscription.adaptor.`in`.web

import com.save.everyapart.subscription.application.port.`in`.SubscriptionQuery
import com.save.everyapart.subscription.application.port.`in`.RegisterSubscriptionCommand
import com.save.everyapart.subscription.application.port.`in`.RegisterSubscriptionUsecase
import com.save.everyapart.subscription.application.service.SubscriptionData
import com.save.everyapart.subscription.domain.EmailAddress
import com.save.everyapart.subscription.domain.JwtToken
import com.save.everyapart.subscription.domain.RegionCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubscriptionController(
    val registerSubscriptionUsecase: RegisterSubscriptionUsecase,
    val subscriptionQuery: SubscriptionQuery
) {

    @PostMapping("/subscriptions")
    fun registerSubscribe(@RequestBody request: PostSubscriptionsRequest) : ResponseEntity<Unit> {

        val command = RegisterSubscriptionCommand(
            token = JwtToken(request.token),
            regionCode = RegionCode(request.regionCode),
            emailAddress = EmailAddress(request.mailAddress)
        )
        registerSubscriptionUsecase.register(command)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(
    ) : ResponseEntity<List<SubscriptionData>> {

        val subscriptions = subscriptionQuery.findAll()

        return ResponseEntity(subscriptions, HttpStatus.OK)
    }

//    @GetMapping("/subscriptions/{subscriptionId}")
//    fun getSubscriptions(
//        @PathVariable subscriptionId: String
//    ) : ResponseEntity<Unit> {
//
//
//    }


}
