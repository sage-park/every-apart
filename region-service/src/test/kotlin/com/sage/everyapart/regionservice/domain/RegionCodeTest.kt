package com.sage.everyapart.regionservice.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class RegionCodeTest {

    @Test
    fun `지역번호는 10자리가 아니면 에러를 반환한다`() {
        val regionCode = "11000000"

        val exception = assertThatThrownBy { RegionCode(regionCode) }

        exception.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `지역번호는 10자리면 인스턴스를 생성한다`() {
        val regionCode = "1100000000"

        val result = RegionCode(regionCode)

        assertThat(result.value).isEqualTo(regionCode)
    }



}


