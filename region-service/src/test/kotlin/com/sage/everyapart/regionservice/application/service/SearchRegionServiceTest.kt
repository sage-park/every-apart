package com.sage.everyapart.regionservice.application.service

import com.sage.everyapart.regionservice.application.port.out.SearchRegionPort
import com.sage.everyapart.regionservice.domain.City
import com.sage.everyapart.regionservice.domain.RegionCode
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.willReturn
import org.springframework.data.domain.PageRequest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SearchRegionServiceTest {

    private val getRegionPort: SearchRegionPort = mock()
    lateinit var searchRegionService:SearchRegionService

    @BeforeTest
    fun setUp() {
        searchRegionService = SearchRegionService(getRegionPort)
    }

    /**
     * 요구사항
     * - port 에서 반환한 데이터를 반환한다.
     * - port 에서 반환한 총 데이터 개수를 반환한다.
     */
    @DisplayName("port 에서 반환된 값들을 그대로 반환한다.")
    @Test
    fun firstTest() {

        //given
        val keyword = "keyword"
        val pageable = PageRequest.of(1, 10)
        val cities = listOf(
            City(RegionCode("0000000001"), "Metropolitan City", "cityName01"),
            City(RegionCode("0000000002"), "Metropolitan City02", "cityName02"),
        )
        given(getRegionPort.search(keyword, pageable)).willReturn(cities)
        given(getRegionPort.getTotal(keyword, pageable)).willReturn(10 )

        //when
        val result = searchRegionService.search(keyword, pageable)

        //then
        assertThat(result.total).isEqualTo(10)
        assertThat(result.cities).isEqualTo(cities)
    }





}

