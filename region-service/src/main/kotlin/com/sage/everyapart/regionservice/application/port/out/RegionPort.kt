package com.sage.everyapart.regionservice.application.port.out

import com.sage.everyapart.regionservice.domain.City
import com.sage.everyapart.regionservice.domain.RegionCode
import org.springframework.data.domain.Pageable

interface RegionPort {
    fun search(keyword: String?, pageable: Pageable): List<City>
    fun getTotal(keyword: String?, pageable: Pageable): Long
    fun exist(regionCode: RegionCode): Boolean

}
