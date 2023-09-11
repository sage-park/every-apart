package com.sage.everyapart.apartmentservice.application.service

import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.application.port.out.SearchApartmentTransactionPort
import com.sage.everyapart.apartmentservice.domain.RegionCode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import java.time.LocalDate
import java.time.YearMonth
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SearchApartmentServiceTest {

    private lateinit var searchApartmentTransactionService: SearchApartmentService

    private val searchApartmentTransactionPort = mock<SearchApartmentTransactionPort>()

    @BeforeTest
    fun setUp() {
        searchApartmentTransactionService = SearchApartmentService(searchApartmentTransactionPort)
    }

    @DisplayName("port 반환값을 그대로 반환한다.")
    @Test
    fun test() {

        //given
        val regionCode = RegionCode("1100000000")
        val dealDate = YearMonth.of(2023, 6)

        val mockResult = listOf(TransactionData(
            regionCode = regionCode.code,
            regionName = "regionName",
            money = 1000,
            dealDate = LocalDate.of(2023, 3, 3),
            apartName = "apartName",
            floorArea = "floorArea",
            floor = 1
        ))

        given(searchApartmentTransactionPort.searchTransactions(regionCode, dealDate))
            .willReturn(mockResult)

        //when
        val result = searchApartmentTransactionService.search(regionCode, dealDate)

        //then
        assertThat(result).isEqualTo(mockResult)


    }

}
