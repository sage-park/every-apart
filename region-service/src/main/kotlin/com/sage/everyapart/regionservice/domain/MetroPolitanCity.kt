package com.sage.everyapart.regionservice.domain

class MetroPolitanCity {

    val regionCode: RegionCode

    constructor(regionCode: RegionCode) {

        //regionCode.value 는 10자리 수 이다. 3번째부터 끝까지 0이 아니면 illegalArgumentException을 반환한다.
        if (!isValidRegionCode(regionCode)) {
            throw IllegalArgumentException("regionCode 가 올바르지 않습니다.")
        }

        this.regionCode = regionCode
    }

    private fun isValidRegionCode(regionCode: RegionCode): Boolean {
        for (i in 2 until regionCode.value.length)
            if (regionCode.value[i] != '0') {
                return false
            }
        return true
    }


}
