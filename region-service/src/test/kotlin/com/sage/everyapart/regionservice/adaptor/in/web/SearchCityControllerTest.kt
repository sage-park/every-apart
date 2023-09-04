package com.sage.everyapart.regionservice.adaptor.`in`.web

import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.sage.everyapart.regionservice.application.port.`in`.SearchCityQuery
import com.sage.everyapart.regionservice.application.service.SearchCityResponse
import com.sage.everyapart.regionservice.domain.City
import com.sage.everyapart.regionservice.domain.RegionCode
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(controllers = [SearchCityController::class])
class SearchCityControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var searchCityQuery: SearchCityQuery


    @Nested
    @DisplayName("GET /cities")
    inner class GetCities{

        @DisplayName("keyword 를 param 으로 넘기지 않으면 query에 blank string 으로 넘긴다.")
        @Test
        fun ifKeywordNull() {

            val result = mockMvc.perform(
                get("/cities")
            )

            then(searchCityQuery).should().search(eq(""), any())
        }

        @DisplayName("keyword가 있을 경우 keyword 를 param 으로 넘긴다.")
        @Test
        fun ifKeywordExist() {

            val keyword = "keyword"

            val result = mockMvc.perform(
                get("/cities")
                    .param("keyword", keyword)
            )

            then(searchCityQuery).should().search(eq(keyword), any())

        }

        @DisplayName("page와 size 가 있을 경우 pageable 을 넘긴다.")
        @Test
        fun ifExistPage() {

            val result = mockMvc.perform(
                get("/cities")
                    .param("size", "2")
                    .param("page", "3")
            )

            then(searchCityQuery).should().search(any(), eq(PageRequest.of(3, 2)))

        }

        @DisplayName("만약 query 결과가 반환되었을떄 그대로 body 로 반환하고 status 는 200으로 반환한다.")
        @Test
        fun ifSuccess() {

            //given
            val cities = listOf(
                City(RegionCode("0000000001"), "Metropolitan City", "cityName01"),
                City(RegionCode("0000000002"), "Metropolitan City02", "cityName02"),
            )
            given(searchCityQuery.search(any(), any())).willReturn(SearchCityResponse(cities, 10))

            //when
            val result = mockMvc.perform(
                get("/cities")
            )

            //then
            result.andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.total").value(10))
                .andExpect(jsonPath("$.cities").isArray)
                .andExpect(jsonPath("$.cities", hasSize<City>(2)))
                .andExpect(jsonPath("$.cities[0].regionCode.value").value("0000000001"))
                .andExpect(jsonPath("$.cities[0].cityName").value("cityName01"))
                .andExpect(jsonPath("$.cities[1].regionCode.value").value("0000000002"))
                .andExpect(jsonPath("$.cities[1].cityName").value("cityName02"))
        }

    }


}

