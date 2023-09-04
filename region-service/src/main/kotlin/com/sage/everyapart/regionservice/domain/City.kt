package com.sage.everyapart.regionservice.domain

class City {
    val regionCode: RegionCode
    val metropolitanCityName: String
    val cityName: String

    constructor(regionCode: RegionCode, metropolitanCityName: String, cityName: String) {
        this.regionCode = regionCode
        this.metropolitanCityName = metropolitanCityName
        this.cityName = cityName
    }
}
