package com.sage.everyapart.apartmentservice.domain

class RegionCode {

    val code:String

    constructor(code: String) {

        if (code.length != 10) {
            throw IllegalArgumentException("지역코드는 10자리여야 합니다.")
        }

        this.code = code
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegionCode

        return code == other.code
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }


}
