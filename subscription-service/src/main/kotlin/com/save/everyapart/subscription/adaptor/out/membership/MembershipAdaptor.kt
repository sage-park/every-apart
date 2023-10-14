package com.save.everyapart.subscription.adaptor.out.membership

import com.fasterxml.jackson.databind.ObjectMapper
import com.save.everyapart.subscription.application.port.out.MembershipPort
import com.save.everyapart.subscription.domain.JwtToken
import com.save.everyapart.subscription.domain.Membership
import com.save.everyapart.subscription.domain.MembershipId
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class MembershipAdaptor(
    @Value("\${service.membership.url}")
    val membershipUrl: String,
    val objectMapper: ObjectMapper
) : MembershipPort {


    override fun isValid(token: JwtToken): Boolean {

        val httpClient = HttpClient.newBuilder().build()

        val body = objectMapper.writeValueAsString(ValidateTokenRequest(token.token))

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${membershipUrl}/membership/token-validate"))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .header("Content-Type", "application/json")
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body() == "true"
    }

    override fun getMembershipInfo(token: JwtToken): Membership {

        val httpClient = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${membershipUrl}/membership/${token.token}"))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val responseBody = objectMapper.readValue(response.body(), GetMembershipResponse::class.java)

        return Membership(
            membershipId = MembershipId(responseBody.membershipId),
        )
    }
}
