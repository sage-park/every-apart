package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "response")
data class ApartmentTransactionDataXmlResponse(
    @JsonProperty("header")
    @JacksonXmlProperty(localName = "header")
    val header: Header,

    @JsonProperty("body")
    @JacksonXmlProperty(localName = "body")
    val body: Body
)

