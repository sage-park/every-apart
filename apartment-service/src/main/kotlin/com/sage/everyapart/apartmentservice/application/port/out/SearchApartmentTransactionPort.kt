package com.sage.everyapart.apartmentservice.application.port.out

import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.domain.RegionCode
import com.sage.everyapart.apartmentservice.domain.Transaction
import java.time.YearMonth

interface SearchApartmentTransactionPort {
    fun searchTransactions(regionCode: RegionCode, dealDate: YearMonth): List<TransactionData>

}

