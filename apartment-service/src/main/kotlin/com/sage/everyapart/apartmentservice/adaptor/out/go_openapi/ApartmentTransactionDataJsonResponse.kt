package com.sage.everyapart.apartmentservice.adaptor.out.go_openapi

import com.fasterxml.jackson.annotation.JsonProperty

data class ApartmentTransactionDataJsonResponse(
    @JsonProperty("response")
    val response: Response,
)

data class Response(
    @JsonProperty("header")
    val header: Header,
    @JsonProperty("body")
    val body: Body,
)

