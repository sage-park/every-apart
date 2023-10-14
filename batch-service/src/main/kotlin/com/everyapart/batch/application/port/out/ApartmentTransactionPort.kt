package com.everyapart.batch.application.port.out

import com.everyapart.batch.domain.ApartmentTransaction

interface ApartmentTransactionPort {
    fun getCurrentMonthTransactions(regionCode: String): List<ApartmentTransaction>

}
