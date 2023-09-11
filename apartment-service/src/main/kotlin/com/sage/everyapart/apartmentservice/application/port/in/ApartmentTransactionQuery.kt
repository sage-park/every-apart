package com.sage.everyapart.apartmentservice.application.port.`in`

import com.sage.everyapart.apartmentservice.domain.RegionCode
import java.time.YearMonth

interface ApartmentTransactionQuery {
    fun search(regionCode: RegionCode, date: YearMonth):List<TransactionData>

}
