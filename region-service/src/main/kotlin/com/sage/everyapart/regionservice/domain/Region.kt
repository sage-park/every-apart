package com.sage.everyapart.regionservice.domain

class Region {

    val isDeleted: Boolean
    val regionCode: RegionCode


    constructor(regionCode: RegionCode, isDeleted: Boolean) {
        this.regionCode = regionCode
        this.isDeleted = isDeleted
    }
}
