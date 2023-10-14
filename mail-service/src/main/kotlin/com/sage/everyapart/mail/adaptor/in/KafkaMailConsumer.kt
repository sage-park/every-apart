package com.sage.everyapart.mail.adaptor.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.sage.everyapart.mail.adaptor.out.gmail.GmailAdaptor
import com.sage.everyapart.mail.domain.EmailMessage
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Properties

@Component
class KafkaMailConsumer(
    val gmailAdaptor: GmailAdaptor,
    @Value("\${kafka.clusters.bootstrapservers}")
    val bootstrapServers: String,
    @Value("\${everyapart.kafka.topic.mail}")
    val topic: String
) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    private final val consumer: KafkaConsumer<String, String>

    init {

        val properties = Properties()
        properties.put("bootstrap.servers", bootstrapServers)
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        properties.put("group.id", "test")

        consumer = KafkaConsumer<String, String>(properties)

        consumer.subscribe(listOf(topic))

        val consumerThread = Thread {

            val objectMapper = ObjectMapper()

            while (true) {
                val records = consumer.poll(100)

                for (record in records) {
                    val message = record.value()

                    log.info("message : $message")

                    gmailAdaptor.sendMail(EmailMessage(
                        "{toMailAddress}",
                        "test",
                        message
                    ))

                    log.info("success")
                }

            }

        }

        consumerThread.start()
    }


}
