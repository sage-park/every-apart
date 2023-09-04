package com.sage.everyapart.regionservice.application.port.out

import com.sage.everyapart.regionservice.domain.City
import org.springframework.data.domain.Pageable

interface SearchRegionPort {
    fun search(keyword: String?, pageable: Pageable): List<City>
    fun getTotal(keyword: String?, pageable: Pageable): Long

}
