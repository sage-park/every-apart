package com.sage.everyapart.regionservice.application.port.`in`

import com.sage.everyapart.regionservice.application.service.SearchCityResponse
import com.sage.everyapart.regionservice.domain.City
import org.springframework.data.domain.Pageable

interface SearchCityQuery {
    fun search(keyword: String?, pageable: Pageable): SearchCityResponse

}
