package com.sage.everyapart.membershipservice.application.service

import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipCommand
import com.sage.everyapart.membershipservice.application.port.out.FindMembershipPort
import com.sage.everyapart.membershipservice.application.port.out.SaveMembershipPort
import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.exception.MembershipAlreadyExistException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
import org.junit.jupiter.api.DisplayName
import org.mockito.kotlin.*
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.BeforeTest
import kotlin.test.Test

class RegisterMembershipServiceTest {

    val saveMembershipPort: SaveMembershipPort = mock()
    val findMembershipPort: FindMembershipPort = mock()
    val passwordEncoder: PasswordEncoder = mock()
    lateinit var registerMembershipService: RegisterMembershipService

    @BeforeTest
    fun setup() {
        registerMembershipService = RegisterMembershipService(
            findMembershipPort = findMembershipPort,
            saveMembershipPort = saveMembershipPort,
            passwordEncoder = passwordEncoder
        )
    }

    @DisplayName("id 가 미리 존재하면 에러를 반환한다.")
    @Test
    fun ifAlreadyExist() {

        //given
        val command = RegisterMembershipCommand(
            id = "user01",
            password = "testPassword"
        )
        given(findMembershipPort.exist(any())).willReturn(true)

        //when
        val catchException = catchException {
            registerMembershipService.register(command)
        }

        //then
        assertThat(catchException).isInstanceOf(MembershipAlreadyExistException::class.java)
    }

    @DisplayName("registerMembershipPort를 통해 membership 을 생성한다.")
    @Test
    fun registerMembership() {

        //given
        val command = RegisterMembershipCommand(
            id = "user01",
            password = "password01"
        )

        given(passwordEncoder.encode(any())).willReturn("encodedPassword")

        given(findMembershipPort.exist(any())).willReturn(false)

        //when
        registerMembershipService.register(command)

        //then
        val argumentCaptor = argumentCaptor<Membership>()
        then(saveMembershipPort).should()
            .save(argumentCaptor.capture())
        val membership = argumentCaptor.firstValue

        assertThat(membership.membershipId.membershipId).isEqualTo(command.id)
        assertThat(membership.password).isEqualTo("encodedPassword")
        assertThat(membership.isValid).isTrue()

    }



}
