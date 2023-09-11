package com.sage.everyapart.apartmentservice.application.port.`in`

import java.time.LocalDate

data class TransactionData(
    val regionCode: String,
    val regionName: String,
    val money: Long,
    val dealDate: LocalDate,
    val apartName: String,
    val floorArea: String,
    val floor: Int
)
