package com.save.everyapart.subscription.adaptor.`in`.web

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@WebMvcTest(controllers = [SubscriptionController::class])
class SubscriptionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc


    /**
     * 구독 등록 API
     */
    @DisplayName("구독 등록")
    @Nested
    inner class Subscribe {

        @Test
        fun firstTest() {

            //when
            val result = mockMvc.perform(
                post("/subscriptions")
            )

            //then
            result
                .andExpect(status().isOk)


        }

    }


}
