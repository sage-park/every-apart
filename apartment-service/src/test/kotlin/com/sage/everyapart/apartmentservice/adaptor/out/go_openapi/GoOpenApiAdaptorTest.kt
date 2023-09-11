package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.sage.everyapart.apartmentservice.domain.RegionCode
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import java.time.LocalDate
import java.time.YearMonth
import kotlin.test.BeforeTest
import kotlin.test.Test

class GoOpenApiAdaptorTest{

    private val goOpenApiClient: GoOpenApiClient = mock()
    lateinit var goOpenApiAdaptor: GoOpenApiAdaptor

    @BeforeTest
    fun setUp() {
        goOpenApiAdaptor = GoOpenApiAdaptor(goOpenApiClient = goOpenApiClient)
    }

    @DisplayName("api client 반환값을 타입에 맞게 매핑하여 반환한다.")
    @Test
    fun test() {
        val mockResult = ApartmentTransactionDataJsonResponse(
            Response(
                Header(
                    resultCode = "200",
                    resultMsg = "성공"
                ),
                Body(
                    items = Items(
                        item = listOf(
                            Item(
                                지역코드 = "1100000000",
                                법정동 = "법정동",
                                거래금액 = "100000",
                                년 = "2023",
                                월 = "1",
                                일 = "2",
                                아파트 = "아파트",
                                전용면적 = "3",
                                층 = "5",
                                거래유형 = "",
                                등기일자 = "",
                                해제여부 = "",
                                해제사유발생일 = null,
                                건축년도 = "5",
                                지번 = "",
                                중개사소재지 = null,
                            )

                        )
                    ),
                    numOfRows = 25,
                    pageNo = 0,
                    totalCount = 1
                )
            )
        )

        given(goOpenApiClient.getApartmentTransactionData(any(), any())).willReturn(mockResult)

        //when
        val result = goOpenApiAdaptor.searchTransactions(RegionCode("1100000000"), YearMonth.of(2023, 3))

        //then
        assertThat(result[0].regionCode).isEqualTo("1100000000")
        assertThat(result[0].regionName).isEqualTo("법정동")
        assertThat(result[0].money).isEqualTo(100000)
        assertThat(result[0].dealDate).isEqualTo(LocalDate.of(2023, 1, 2))
        assertThat(result[0].apartName).isEqualTo("아파트")
        assertThat(result[0].floorArea).isEqualTo("3")
        assertThat(result[0].floor).isEqualTo(5)

    }

}
