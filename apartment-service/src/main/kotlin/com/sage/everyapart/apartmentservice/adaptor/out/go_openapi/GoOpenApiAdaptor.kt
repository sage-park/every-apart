package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.application.port.out.SearchApartmentTransactionPort
import com.sage.everyapart.apartmentservice.domain.RegionCode
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class GoOpenApiAdaptor(
    private val goOpenApiClient: GoOpenApiClient
) : SearchApartmentTransactionPort {

    override fun searchTransactions(regionCode: RegionCode, dealDate: YearMonth): List<TransactionData> {
        val response = goOpenApiClient.getApartmentTransactionData(regionCode, dealDate)

        return response.response.body.items.item.map { TransactionData(
            it.지역코드,
            it.법정동,
            it.거래금액.trim().replace(",", "").toLong(),
            LocalDate.of(it.년.toInt(), it.월.toInt(), it.일.toInt()),
            it.아파트,
            it.전용면적,
            it.층.toInt()
        ) }

    }
}
