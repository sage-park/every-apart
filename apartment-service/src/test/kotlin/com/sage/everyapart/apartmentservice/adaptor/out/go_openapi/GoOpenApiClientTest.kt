package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.sage.everyapart.apartmentservice.domain.RegionCode
import com.sage.everyapart.apartmentservice.exception.GoApiCallFailException
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.*
import org.mockito.BDDMockito.*
import org.mockito.kotlin.mock
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.io.FileInputStream
import java.net.URI
import java.time.YearMonth
import java.util.Properties

internal class GoOpenApiClientTest : BehaviorSpec({

    val properties = Properties()
    properties.load(FileInputStream("src/test/resources/application.properties"))

    val accessKey = properties.getProperty("everyapart.goopenapi.accesskey")

    val restTemplate = mock<RestTemplate>()
    val restTemplateBuilder = mock<RestTemplateBuilder>(){
        on{build()}.thenReturn(restTemplate)
    }
    val goOpenApiClient = GoOpenApiClient(accessKey, restTemplateBuilder)

    Given("만약 api 응답으로 에러가 반환되면") {

        given(restTemplate.getForObject(any(URI::class.java), eq(ApartmentTransactionDataJsonResponse::class.java)))
            .willThrow(RuntimeException())

        When("호출시"){

            val exception = assertThatThrownBy { goOpenApiClient.getApartmentTransactionData(RegionCode("1100000000"), YearMonth.of(2015, 12)) }

            Then("에러를 던진다."){
                exception.isInstanceOf(GoApiCallFailException::class.java)
            }
        }

    }

    Given("만약 api 응답이 성공하면") {

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

        given(restTemplate.getForEntity(any(URI::class.java), any(Class::class.java)))
            .willReturn(ResponseEntity.ok(mockResult))

        When("호출시") {

            val response = goOpenApiClient.getApartmentTransactionData(RegionCode("1100000000"), YearMonth.of(2015, 12))

            Then("응답을 그대로 반환한다.") {

                assertThat(response).isEqualTo(mockResult)
            }
        }



    }


})
