package com.everyapart.batch.domain

import java.time.LocalDate

data class ApartmentTransaction(
    val regionCode: String,
    val regionName: String,
    val money: Long,
    val dealDate: LocalDate,
    val apartName: String,
    val floorArea: String,
    val floor: Int
) {

}
