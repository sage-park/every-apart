package com.save.everyapart.subscription.adaptor.out.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import java.util.Properties
import kotlin.test.Test

class MailProducerTest {

    @DisplayName("실제 테스트")
    @Disabled
    @Test
    fun actualCall() {
        //given
        val bootstrapServers = "localhost:9092"
        val topic = "everyapart.mail.send"
        val message = "test 01"

        //when
        MailProducer(
            bootstrapServers,
            topic
        )
            .send(message)

        //then
        //message 전송 되었는지 확인
    }

}
