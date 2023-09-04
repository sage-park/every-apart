package com.sage.everyapart.regionservice.application.service

import com.sage.everyapart.regionservice.application.port.out.SearchRegionPort
import com.sage.everyapart.regionservice.application.port.`in`.SearchCityQuery
import com.sage.everyapart.regionservice.domain.City
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchRegionService(
    private val getRegionPort: SearchRegionPort
): SearchCityQuery{

    override fun search(keyword: String?, pageable: Pageable): SearchCityResponse {

        val cities = getRegionPort.search(keyword, pageable)
        val total = getRegionPort.getTotal(keyword, pageable)
        return SearchCityResponse(cities, total)
    }

}
