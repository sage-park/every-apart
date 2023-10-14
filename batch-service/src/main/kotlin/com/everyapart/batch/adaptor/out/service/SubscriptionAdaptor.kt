package com.everyapart.batch.adaptor.out.service

import com.everyapart.batch.application.port.out.SubscriptionPort
import com.everyapart.batch.domain.Subscription
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class SubscriptionAdaptor(
    @Value("\${service.subscription.url}")
    val subscriptionUrl: String,
    val objectMapper: ObjectMapper
): SubscriptionPort {
    override fun getAll(): List<Subscription> {

        val httpClient = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${subscriptionUrl}/subscriptions"))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val responseBody = objectMapper.readValue(response.body(), Array<SubscriptionData>::class.java).toList()

        return responseBody.stream()
            .map {
                Subscription(
                    subscriptionId = it.subscriptionId,
                    membershipId = it.membershipId,
                    regionCode = it.regionCode,
                    active = it.active,
                    emailAddress = it.emailAddress
                )
            }
            .toList()
    }
}
