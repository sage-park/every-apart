package com.sage.everyapart.apartmentservice.adaptor.`in`.web

import com.sage.everyapart.apartmentservice.application.port.`in`.ApartmentTransactionQuery
import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.domain.RegionCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.YearMonth

@RestController
class SearchApartmentTransactionController(
    private val apartmentTransactionQuery: ApartmentTransactionQuery
) {

    @GetMapping("/transactions")
    fun searchTransactions(
        @RequestParam regionCode: String,
        @RequestParam year: Int,
        @RequestParam month: Int,
    ): ResponseEntity<List<TransactionData>> {

        val result = apartmentTransactionQuery.search(RegionCode(regionCode), YearMonth.of(year, month))

        return ResponseEntity(result, HttpStatus.OK)
    }

}
