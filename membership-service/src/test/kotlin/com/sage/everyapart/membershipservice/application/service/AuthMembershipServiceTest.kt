package com.sage.everyapart.membershipservice.application.service

import com.sage.everyapart.membershipservice.application.port.`in`.AuthMembershipUsecase
import com.sage.everyapart.membershipservice.application.port.`in`.RefreshTokenCommand
import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.TokenPort
import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId
import com.sage.everyapart.membershipservice.exception.ErrorCode
import com.sage.everyapart.membershipservice.exception.MembershipException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.BeforeTest
import kotlin.test.Test

class AuthMembershipServiceTest {

    val saveMembershipPort = mock<SaveMembershipPort>()
    val findMembershipPort = mock<FindMembershipPort>()
    val passwordEncoder = mock<PasswordEncoder>()
    val tokenPort = mock<TokenPort>()

    lateinit var authMembershipService: AuthMembershipUsecase

    @BeforeTest
    fun setup() {
        authMembershipService = AuthMembershipService(
            tokenPort = tokenPort,
            findMembershipPort = findMembershipPort,
            saveMembershipPort = saveMembershipPort,
            passwordEncoder = passwordEncoder
        )
    }

    @DisplayName("loginMembership")
    @Nested
    inner class LoginMembership {

        @DisplayName("만약 찾은 membership 이 유효하지 않다면 에러를 반환한다.")
        @Test
        fun ifInvalidMembership() {

            val membership = mock<Membership>(){
                on { isValid } doReturn false
            }
            given(findMembershipPort.findMembership(any()))
                .willReturn(membership)

            val exception = assertThrows<MembershipException> {
                authMembershipService.loginMembership(
                    LoginMembershipCommand("1", "password")
                )
            }

            assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_MEMBERSHIP)
        }

        @DisplayName("만약 찾은 membership이 유효하고")
        @Nested
        inner class IfValid {

            @BeforeTest
            fun setup() {

                val membership = Membership(
                    membershipId = MembershipId("1"),
                    password = "encodedPassword",
                    isValid = true
                )
                given(findMembershipPort.findMembership(any()))
                    .willReturn(membership)
            }

            @DisplayName("만약 password가 틀릴 경우 에러를 반환한다.")
            @Test
            fun ifInvalidPassword() {

                //given
                given(passwordEncoder.matches(any(), any()))
                    .willReturn(false)

                //when
                val exception = catchException {
                    authMembershipService.loginMembership(
                        LoginMembershipCommand("1", "password")
                    )
                }

                //then
                assertThat(exception).isInstanceOf(MembershipException::class.java)
                assertThat((exception as MembershipException).errorCode).isEqualTo(ErrorCode.INVALID_PASSWORD)

            }

            @DisplayName("만약 password가 맞을 경우 jwtToken 을 반환한다.")
            @Test
            fun ifCorrectPassword() {

                given(passwordEncoder.matches(any(), any()))
                    .willReturn(true)

                given(tokenPort.generateJwtToken(any()))
                    .willReturn("jwtToken01")

                given(tokenPort.generateRefreshToken(any()))
                    .willReturn("refreshToken01")

                val result = authMembershipService.loginMembership(
                    LoginMembershipCommand(
                        "1", "password"
                    )
                )

                assertThat(result.membershipId).isEqualTo("1")
                assertThat(result.jwtToken).isEqualTo("jwtToken01")
                assertThat(result.refreshToken).isEqualTo("refreshToken01")

            }

        }

    }

    @DisplayName("refreshJwtTokenByRefreshToken")
    @Nested
    inner class RefreshJwtTokenByRefreshToken {

        @DisplayName("만약 refreshToken 이 유효하지 않다면 에러를 반환한다.")
        @Test
        fun ifInvalidToken() {

            given(tokenPort.validateJwtToken(any()))
                .willReturn(false)

            val catchException = assertThrows<MembershipException> {
                authMembershipService.refreshJwtTokenByRefreshToken(
                    RefreshTokenCommand("refreshToken01")
                )
            }

            assertThat(catchException.errorCode).isEqualTo(ErrorCode.INVALID_TOKEN)
        }

        @DisplayName("만약 refrshToken이 유효하고")
        @Nested
        inner class IfValidToken {

            val membershipId01 =  MembershipId("membershipId01")
            val refreshToken01 = "refreshToken01"


            @BeforeTest
            fun setup() {
                given(tokenPort.validateJwtToken(any()))
                    .willReturn(true)
                given(tokenPort.parseMembershipIdFromToken(refreshToken01))
                    .willReturn(membershipId01)
            }

            @DisplayName("주어진 refreshToken과 등록된 refreshToken이 다르다면 에러를 반환한다.")
            @Test
            fun ifNotEqualsToken() {

                val membership = mock<Membership>() {
                    on{ refreshToken } doReturn "notEqualsToken"
                }
                given(findMembershipPort.findMembership(any()))
                    .willReturn(membership)

                val exception = assertThrows<MembershipException> {
                    authMembershipService.refreshJwtTokenByRefreshToken(
                        RefreshTokenCommand(refreshToken01)
                    )
                }

                assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_TOKEN)

            }

            @DisplayName("주어진 refreshToken과 등록된 refreshToken이 동일하고")
            @Nested
            inner class EqualsRefreshToken {

                val membership = mock<Membership>()

                @BeforeTest
                fun setup() {
                    given(membership.refreshToken).willReturn(refreshToken01)

                    given(findMembershipPort.findMembership(any()))
                        .willReturn(membership)
                }


                @DisplayName("membership이 유효한 상태가 아닐 경우 에러를 반환한다.")
                @Test
                fun ifInvalidMembership() {

                    given(membership.isValid).willReturn(false)


                    val exception = assertThrows<MembershipException> {
                        authMembershipService.refreshJwtTokenByRefreshToken(
                            RefreshTokenCommand(refreshToken01)
                        )
                    }

                    assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_MEMBERSHIP)

                }

                @DisplayName("membership 이 유효한 상태일 경우 jwtToken 을 반환한다.")
                @Test
                fun ifValidMembership() {

                    given(membership.isValid).willReturn(true)

                    val jwtTokenMockResult = "jwtToken01"
                    given(tokenPort.generateJwtToken(any()))
                        .willReturn(jwtTokenMockResult)


                    val jwtToken = authMembershipService.refreshJwtTokenByRefreshToken(
                        RefreshTokenCommand(refreshToken01)
                    )

                    assertThat(jwtToken.jwtToken).isEqualTo(jwtTokenMockResult)
                    assertThat(jwtToken.refreshToken).isEqualTo(refreshToken01)
                    assertThat(jwtToken.membershipId).isEqualTo(membershipId01.membershipId)

                }


            }


        }



    }



}
