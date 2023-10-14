package com.everyapart.batch.adaptor.out.service

import com.everyapart.batch.application.port.out.ApartmentTransactionPort
import com.everyapart.batch.domain.ApartmentTransaction
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate

@Component
class ApartmentTransactionAdaptor(
    @Value("\${service.apartmenttransaction.url}")
    val apartmentTransactionUrl: String,
    val objectMapper: ObjectMapper
) : ApartmentTransactionPort{

    override fun getCurrentMonthTransactions(regionCode: String): List<ApartmentTransaction> {
        val httpClient = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${apartmentTransactionUrl}/transactions?regionCode=${regionCode}&month=${LocalDate.now().month.value}&year=${LocalDate.now().year}"))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val responseBody = objectMapper.readValue(response.body(), Array<ApartmentTransactionData>::class.java).toList()

        return responseBody.stream()
            .map {
                ApartmentTransaction(
                    regionCode = it.regionCode,
                    regionName = it.regionName,
                    money = it.money,
                    dealDate = it.dealDate,
                    apartName = it.apartName,
                    floorArea = it.floorArea,
                    floor = it.floor
                )
            }
            .toList()
    }

}
