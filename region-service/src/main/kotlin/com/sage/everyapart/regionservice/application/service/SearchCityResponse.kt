package com.sage.everyapart.regionservice.application.service

import com.sage.everyapart.regionservice.domain.City

data class SearchCityResponse(
    val cities: List<City>,
    val total: Long
) {

}
