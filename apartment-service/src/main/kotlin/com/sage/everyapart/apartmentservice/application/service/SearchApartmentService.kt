package com.sage.everyapart.apartmentservice.application.service

import com.sage.everyapart.apartmentservice.application.port.`in`.ApartmentTransactionQuery
import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.application.port.out.SearchApartmentTransactionPort
import com.sage.everyapart.apartmentservice.domain.RegionCode
import com.sage.everyapart.apartmentservice.domain.Transaction
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class SearchApartmentService(val searchTransactionPort: SearchApartmentTransactionPort): ApartmentTransactionQuery {

    override fun search(regionCode: RegionCode, date: YearMonth): List<TransactionData> {

        return searchTransactionPort.searchTransactions(regionCode, date)
    }

}
