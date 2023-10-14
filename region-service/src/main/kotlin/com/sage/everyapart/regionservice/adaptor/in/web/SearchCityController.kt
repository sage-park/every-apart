package com.sage.everyapart.regionservice.adaptor.`in`.web

import com.sage.everyapart.regionservice.application.port.`in`.RegionQuery
import com.sage.everyapart.regionservice.application.port.`in`.SearchCityQuery
import com.sage.everyapart.regionservice.application.service.SearchCityResponse
import com.sage.everyapart.regionservice.domain.RegionCode
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchCityController(
    private val searchCityQuery: SearchCityQuery,
    private val regionQuery: RegionQuery
) {

    @GetMapping("/cities")
    fun searchCity(
        @RequestParam(defaultValue = "") keyword: String,
        pageable: Pageable
    ): ResponseEntity<SearchCityResponse> {

        val searchResult = searchCityQuery.search(keyword, pageable)

        return ResponseEntity(searchResult, HttpStatus.OK)

    }

    @GetMapping("/region/{regionCode}/exist")
    fun searchCityByRegionCode(
        @PathVariable regionCode: String,
    ): ResponseEntity<Boolean> {

        val isExist = regionQuery.exist(RegionCode(regionCode))

        return ResponseEntity(isExist, HttpStatus.OK)

    }

}
