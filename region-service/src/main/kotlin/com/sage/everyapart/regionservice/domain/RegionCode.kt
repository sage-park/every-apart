package com.sage.everyapart.regionservice.domain

class RegionCode {

    val value:String

    constructor(value: String) {

        if (value.length != 10) {
            throw IllegalArgumentException("지역번호는 10자리여야 합니다.")
        }

        this.value = value
    }
}
