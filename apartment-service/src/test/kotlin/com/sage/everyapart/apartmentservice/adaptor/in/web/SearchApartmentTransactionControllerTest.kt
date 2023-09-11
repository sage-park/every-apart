package com.sage.everyapart.apartmentservice.adaptor.`in`.web

import com.sage.everyapart.apartmentservice.application.port.`in`.ApartmentTransactionQuery
import com.sage.everyapart.apartmentservice.application.port.`in`.TransactionData
import com.sage.everyapart.apartmentservice.domain.RegionCode
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.YearMonth
import kotlin.test.Test

@WebMvcTest(controllers = [SearchApartmentTransactionController::class])
class SearchApartmentTransactionControllerTest {

    @MockBean
    private lateinit var apartmentTransactionQuery:ApartmentTransactionQuery

    @Autowired
    private lateinit var mockMvc: MockMvc

    /**
     * 요구사항
     * param 은 RegionCode, date를 받는다.
     * 받은 param 으로 command 를 생성하여 usecase 로 넘긴다.
     */
    @DisplayName("""
        regionCode, year, month 파라미터를 usecase 로 넘긴다. 
        그 후 검색결과를 body 로 반환한다.
    """)
    @Test
    fun test() {

        //given
        val regionCode = "1100000000"
        val year = "2023"
        val month = "3"
        val mockResult = listOf(
            TransactionData(
                regionCode = regionCode,
                regionName = "regionName",
                money = 1000,
                dealDate = LocalDate.of(2023, 3, 3),
                apartName = "apartName",
                floorArea = "floorArea",
                floor = 1
            )
        )
        given(apartmentTransactionQuery.search(RegionCode(regionCode), YearMonth.of(year.toInt(), month.toInt())))
            .willReturn(mockResult)

        //when
        val result = mockMvc.perform(
            get("/transactions")
                .param("regionCode", regionCode)
                .param("year", year)
                .param("month", month)
        )

        //then
        result.andExpect(status().isOk)
            .andExpect(jsonPath("$[0].regionCode").value(regionCode))
            .andExpect(jsonPath("$[0].regionName").value("regionName"))
            .andExpect(jsonPath("$[0].money").value(1000))
            .andExpect(jsonPath("$[0].dealDate").value("2023-03-03"))
            .andExpect(jsonPath("$[0].apartName").value("apartName"))
            .andExpect(jsonPath("$[0].floorArea").value("floorArea"))
            .andExpect(jsonPath("$[0].floor").value(1))

        then(apartmentTransactionQuery).should().search(RegionCode(regionCode), YearMonth.of(year.toInt(), month.toInt()))
    }

    @Test
    fun `만약 regionCode 가 없으면 400 에러를 반환한다`() {
        //when
        val result = mockMvc.perform(
            get("/transactions")
                .param("year", "2023")
                .param("month", "3")
        )

        //then
        result.andExpect(status().is4xxClientError)
    }

    @Test
    fun `만약 year와 month가 없으면 400 에러를 반환한다`() {
        //when
        val result = mockMvc.perform(
            get("/transactions")
                .param("regionCode", "1100000000")
                .param("year", "2023")
        )
        result.andExpect(status().is4xxClientError)

        val result02 = mockMvc.perform(
            get("/transactions")
                .param("regionCode", "1100000000")
                .param("month", "3")
        )
        result02.andExpect(status().is4xxClientError)
    }






}
