package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.cfg.CoercionConfig
import com.sage.everyapart.apartmentservice.domain.RegionCode
import com.sage.everyapart.apartmentservice.exception.GoApiCallFailException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import java.net.URI
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Component
class GoOpenApiClient(
    @Value("\${everyapart.goopenapi.accesskey}")
    private val accessKey: String,

    private val restTemplateBuilder: RestTemplateBuilder,
) {

    fun getApartmentTransactionData(regionCode: RegionCode, dealDate: YearMonth): ApartmentTransactionDataJsonResponse {

        val restTemplate = restTemplateBuilder.build()

        val url = URI("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade?LAWD_CD=${regionCode.code.substring(0, 5)}&DEAL_YMD=${dealDate.format(DateTimeFormatter.ofPattern("yyyyMM"))}&serviceKey=$accessKey")

        try {

            val responseBody = restTemplate.getForEntity(url, String::class.java).body

            val objectMapper = ObjectMapper()
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

            val responseEntity = objectMapper.readValue(responseBody, ApartmentTransactionDataJsonResponse::class.java)


            return responseEntity ?: throw RuntimeException()
        } catch (e: Exception){
            e.printStackTrace()

            throw GoApiCallFailException("공공데이터 API 호출시 에러가 발생했습니다.", e)
        }


    }

}
