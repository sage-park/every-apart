package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.fasterxml.jackson.dataformat.xml.XmlMapper


class ApartmentTransactionDataXmlParser {

    private val xmlMapper = XmlMapper()
    fun parse(data: String): ApartmentTransactionDataXmlResponse {

        return xmlMapper.readValue(updateStandaloneValue(data), ApartmentTransactionDataXmlResponse::class.java)
    }

    private fun updateStandaloneValue(xmlString: String): String {
        val regex = Regex("""standalone=["']([^"']*)["']""") // standalone 속성값을 찾는 정규식
        val updatedXmlString = regex.replace(xmlString) { matchResult ->
            val standaloneValue = matchResult.groupValues[1]
            if (standaloneValue != "yes") {
                // standalone 속성값이 'yes'가 아닌 경우에만 'yes'로 변경
                """standalone="yes""""
            } else {
                matchResult.value // 이미 standalone 속성값이 'yes'인 경우에는 변경하지 않음
            }
        }
        return updatedXmlString
    }

}
