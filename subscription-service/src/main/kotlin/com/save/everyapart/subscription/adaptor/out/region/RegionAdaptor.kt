package com.save.everyapart.subscription.adaptor.out.region

import com.fasterxml.jackson.databind.ObjectMapper
import com.save.everyapart.subscription.adaptor.out.membership.ValidateTokenRequest
import com.save.everyapart.subscription.application.port.out.RegionPort
import com.save.everyapart.subscription.domain.RegionCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class RegionAdaptor(
    @Value("\${service.region.url}")
    val regionUrl: String,
) : RegionPort {
    override fun exist(regionCode: RegionCode): Boolean {
        val httpClient = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${regionUrl}/region/${regionCode.regionCode}/exist"))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body() == "true"
    }
}
