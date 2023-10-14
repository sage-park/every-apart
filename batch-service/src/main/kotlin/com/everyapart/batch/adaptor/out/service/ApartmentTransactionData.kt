package com.everyapart.batch.adaptor.out.service

import java.time.LocalDate

data class ApartmentTransactionData(
    val regionCode: String,
    val regionName: String,
    val money: Long,
    val dealDate: LocalDate,
    val apartName: String,
    val floorArea: String,
    val floor: Int
) {
}
