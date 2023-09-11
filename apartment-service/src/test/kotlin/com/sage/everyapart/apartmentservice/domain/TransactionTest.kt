package com.sage.everyapart.apartmentservice.domain

import org.assertj.core.api.Assertions
import kotlin.test.Test

class TransactionTest {

    @Test
    fun test() {

        val transaction = Transaction(
            transactionAmount= TransactionAmount(1000000)
        )

    }


}
