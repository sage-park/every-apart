package com.sage.everyapart.membershipservice.adaptor.`in`.web

import com.sage.everyapart.membershipservice.application.port.`in`.AuthMembershipUsecase
import com.sage.everyapart.membershipservice.application.service.LoginMembershipCommand
import com.sage.everyapart.membershipservice.domain.JwtToken
import com.sage.everyapart.membershipservice.domain.MembershipId
import com.sage.everyapart.membershipservice.domain.MembershipJwtToken
import com.sage.everyapart.membershipservice.domain.MembershipRefreshToken
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.mockito.BDDMockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.BeforeTest
import kotlin.test.Test

@WebMvcTest(controllers = [AuthMembershipController::class])
class AuthMembershipControllerTest{

    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var authMembershipUsecase: AuthMembershipUsecase

    @Nested
    @DisplayName("POST /membership/login")
    inner class PostMembershipLogin {

        @DisplayName("만약 request 에 id, password 가 주어지고")
        @Nested
        inner class GivenIdAndPassword {

            val jwtToken = "jwtToken"
            val refreshToken = "refreshToken"

            val body = "{\"membershipId\":\"1\", \"password\": \"password\"}"

            @DisplayName("로그인이 성공한다면")
            @Nested
            inner class IfLoginSuccess {

                @BeforeTest
                fun setup(){

                    val jwtTokenObject = JwtToken(
                        membershipId = MembershipId("1"),
                        jwtToken = MembershipJwtToken(jwtToken),
                        refreshToken = MembershipRefreshToken(refreshToken),
                    )
                    given(authMembershipUsecase.loginMembership(any()))
                        .willReturn(jwtTokenObject)

                }

                @DisplayName("호출시 jwt 토큰, refreshToken을 반환한다.")
                @Test
                fun returnTokens() {

                    val result = mockMvc.perform(
                        post("/membership/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)
                    )

                    result
                        .andDo(print())
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$.jwtToken").value(jwtToken))
                        .andExpect(jsonPath("$.refreshToken").value(refreshToken))

                    val argumentCaptor = argumentCaptor<LoginMembershipCommand>()
                    then(authMembershipUsecase).should().loginMembership(argumentCaptor.capture())

                    val firstValue = argumentCaptor.firstValue
                    assertThat(firstValue.membershipId).isEqualTo("1")
                    assertThat(firstValue.password).isEqualTo("password")

                }



            }

        }

        @DisplayName("만약 request 에 id, password중 하나라도 주어지지 않았다면 400을 반환한다.")
        @Test
        fun returnBadRequest() {

            mockMvc.perform(
                post("/membership/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
                .andExpect(status().is4xxClientError)

            mockMvc.perform(
                post("/membership/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"membershipId\":\"1\"}")
            )
                .andExpect(status().is4xxClientError)


            mockMvc.perform(
                post("/membership/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"password\":\"1\"}")
            )
                .andExpect(status().is4xxClientError)

        }

    }

    @Nested
    @DisplayName("POST /membership/refresh-token")
    inner class PostMembershipRefreshToken {

        @DisplayName("만약 request 에 refreshToken 이 주어지고 refreshToken 에 성공한다면")
        @Nested
        inner class GivenRefreshToken {

            val body = "{\"refreshToken\": \"testRefreshToken\"}"

            @BeforeTest
            fun setup() {
                val jwtToken = JwtToken(
                    membershipId = MembershipId("memershipId01"),
                    jwtToken = MembershipJwtToken("jwtToken01"),
                    refreshToken = MembershipRefreshToken("refreshToken01")
                )
                given(authMembershipUsecase.refreshJwtTokenByRefreshToken(any()))
                    .willReturn(jwtToken)

            }

            @DisplayName("jwtToken과 refreshToken 을 반환한다.")
            @Test
            fun ifSuccess() {

                mockMvc.perform(
                    post("/membership/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.membershipId").value("memershipId01"))
                    .andExpect(jsonPath("$.jwtToken").value("jwtToken01"))
                    .andExpect(jsonPath("$.refreshToken").value("refreshToken01"))
            }


        }

        @DisplayName("만약 request 에 refreshToken 이 주어지지 않는다면 400을 반환한다.")
        @Test
        fun notGivenRefreshToken() {

            mockMvc.perform(
                post("/membership/refresh-token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
                .andExpect(status().is4xxClientError)


        }



    }

    @Nested
    @DisplayName("POST /membership/token-validate")
    inner class PostMembershipTokenValidate {

        @DisplayName("만약 request 에 jwtToken 이 있으면")
        @Nested
        inner class GivenJwtToken {

            val body = "{\"jwtToken\":  \"jwtToken02\"}"

            @DisplayName("validate 성공시 true를 반환한다.")
            @Test
            fun returnValidate() {

                given(authMembershipUsecase.validateJwtToken(any()))
                    .willReturn(true)
                mockMvc.perform(
                    post("/membership/token-validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                    .andExpect(status().isOk)
                    .andExpect(content().string("true"))
            }

            @DisplayName("validate 실패시 false를 반환한다.")
            @Test
            fun ifFail() {

                given(authMembershipUsecase.validateJwtToken(any()))
                    .willReturn(false)

                mockMvc.perform(
                    post("/membership/token-validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                    .andExpect(status().isOk)
                    .andExpect(content().string("false"))

            }

        }

        @DisplayName("만약 request 에 jwtToken이 없다면 400을 반환한다.")
        @Test
        fun notGivenJwtToken() {

            mockMvc.perform(
                post("/membership/token-validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}")
            )
                .andExpect(status().is4xxClientError)

        }



    }



}
