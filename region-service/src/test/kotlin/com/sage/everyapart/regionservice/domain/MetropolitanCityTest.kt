package com.sage.everyapart.regionservice.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class MetropolitanCityTest {

    /**
     * 요구사항
     * regionCode 는 10자리로 되어있다.
     * name 종류는 다음과 같다.
     * - 시도 이름(metropolitanCityName)
     * - 시군구 이름(cityName)
     * - 읍면동 이름(districtName)
     * - 리 이름(villageName)
     * - 삭제여부(isDeleted)
     *
     * 만약 시도 종류면 3번째 글자부터 0 이고 시도 이름만 있다.
     * 만약 시군구 종류면 6번쨰 글자부터 0 이고 시도, 시군구 이름이 있다.
     * 만약 읍면동 종류면 8번째 글자부터 0 이고 시도, 시군구, 읍면동 이름이 있다.
     * 만약 리 종료면 시도, 시군구, 읍면동, 리 이름이 있다.
     *
     */

    @DisplayName(
        """
     regionCode의 3번쨰 글자부터 0이면
     MetropolitanCity 생성시 
     오류를 반환하지 않는다.
    """
    )
    @Test
    fun test01() {
        val regionCode = RegionCode("1100000000")

        val metropolitanCity = MetroPolitanCity(regionCode)

        assertThat(metropolitanCity.regionCode).isEqualTo(regionCode)
    }

    @DisplayName("""
     regionCode의 3번쨰 글자부터 0이 아니면
     MetropolitanCity 생성시
     IllegalArgumentException 을 던진다.
    """)
    @Test
    fun test02() {
        val regionCode = RegionCode("1104000020")

        val exception = assertThatThrownBy { MetroPolitanCity(regionCode) }

        exception.isInstanceOf(IllegalArgumentException::class.java)
    }


}
