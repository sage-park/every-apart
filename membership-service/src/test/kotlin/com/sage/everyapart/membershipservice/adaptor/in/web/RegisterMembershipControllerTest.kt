package com.sage.everyapart.membershipservice.adaptor.`in`.web

import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipCommand
import com.sage.everyapart.membershipservice.application.port.`in`.RegisterMembershipUsecase
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.mockito.BDDMockito.*
import org.mockito.kotlin.argumentCaptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(controllers = [RegisterMembershipController::class])
class RegisterMembershipControllerTest {

    @MockBean
    lateinit var registerMembershipUsecase: RegisterMembershipUsecase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("POST /membership/register")
    inner class PostMembershipRegister {

        @Test
        fun registerTest() {

            val result = mockMvc.perform(
                post("/membership/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"id\": \"user01\",\n  \"password\": \"testPassword\"\n}")
            )

            //then
            result
                .andExpect(status().isCreated)

            val argumentCaptor = argumentCaptor<RegisterMembershipCommand>()
            then(registerMembershipUsecase).should()
                .register(argumentCaptor.capture())

            val actualCommand = argumentCaptor.firstValue
            assertThat(actualCommand.id).isEqualTo("user01")
            assertThat(actualCommand.password).isEqualTo("testPassword")


        }

    }

}
