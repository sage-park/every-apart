package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class Header(
    @JsonProperty("resultCode")
    @JacksonXmlProperty(localName = "resultCode")
    val resultCode: String,

    @JsonProperty("resultMsg")
    @JacksonXmlProperty(localName = "resultMsg")
    val resultMsg: String
)

data class Body(
    @JsonProperty("items")
    val items: Items,

    @JsonProperty("numOfRows")
    val numOfRows: Int,

    @JsonProperty("pageNo")
    val pageNo: Int,

    @JsonProperty("totalCount")
    val totalCount: Int
)

data class Items(
    @JsonProperty("item")
    val item: List<Item>
);

data class Item(
    @JsonProperty("거래금액")
    @JacksonXmlProperty(localName = "거래금액")
    val 거래금액: String,

    @JsonProperty("거래유형")
    @JacksonXmlProperty(localName = "거래유형")
    val 거래유형: String?,

    @JsonProperty("건축년도")
    @JacksonXmlProperty(localName = "건축년도")
    val 건축년도: String,

    @JsonProperty("년")
    @JacksonXmlProperty(localName = "년")
    val 년: String,

    @JsonProperty("등기일자")
    @JacksonXmlProperty(localName = "등기일자")
    val 등기일자: String?,

    @JsonProperty("법정동")
    @JacksonXmlProperty(localName = "법정동")
    val 법정동: String,

    @JsonProperty("아파트")
    @JacksonXmlProperty(localName = "아파트")
    val 아파트: String,

    @JsonProperty("월")
    @JacksonXmlProperty(localName = "월")
    val 월: String,

    @JsonProperty("일")
    @JacksonXmlProperty(localName = "일")
    val 일: String,

    @JsonProperty("전용면적")
    @JacksonXmlProperty(localName = "전용면적")
    val 전용면적: String,

    @JsonProperty("중개사소재지")
    @JacksonXmlProperty(localName = "중개사소재지")
    val 중개사소재지: String?,

    @JsonProperty("지번")
    @JacksonXmlProperty(localName = "지번")
    val 지번: String,

    @JsonProperty("지역코드")
    @JacksonXmlProperty(localName = "지역코드")
    val 지역코드: String,

    @JsonProperty("층")
    @JacksonXmlProperty(localName = "층")
    val 층: String,

    @JsonProperty("해제여부")
    @JacksonXmlProperty(localName = "해제여부")
    val 해제여부: String?,

    @JsonProperty("해제사유발생일")
    @JacksonXmlProperty(localName = "해제사유발생일")
    val 해제사유발생일: String?,



)
