package com.sage.everyapart.regionservice.application.service

import com.sage.everyapart.regionservice.application.port.`in`.RegionQuery
import com.sage.everyapart.regionservice.application.port.out.RegionPort
import com.sage.everyapart.regionservice.application.port.`in`.SearchCityQuery
import com.sage.everyapart.regionservice.domain.RegionCode
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchRegionService(
    private val regionPort: RegionPort
): SearchCityQuery, RegionQuery{

    override fun search(keyword: String?, pageable: Pageable): SearchCityResponse {

        val cities = regionPort.search(keyword, pageable)
        val total = regionPort.getTotal(keyword, pageable)
        return SearchCityResponse(cities, total)
    }

    override fun exist(regionCode: RegionCode): Boolean {
        return regionPort.exist(regionCode)
    }

}
