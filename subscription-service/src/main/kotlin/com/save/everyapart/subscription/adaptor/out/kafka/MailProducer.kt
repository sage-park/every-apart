package com.save.everyapart.subscription.adaptor.out.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Properties

@Component
class MailProducer(
    @Value("\${kafka.clusters.bootstrapservers}")
    val bootstrapServers: String,

    @Value("\${everyapart.kafka.topic.mail}")
    val topic: String
) {

    private lateinit var producer: KafkaProducer<String, String>

    init {
        val props = Properties()
        props.put("bootstrap.servers", bootstrapServers)
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


        producer = KafkaProducer<String, String>(props)
    }

    fun send(message: String) {

        val record = ProducerRecord(topic, message, "test success")

        producer.send(record, { metadata, exception ->
            println("$metadata, $exception")
        })
    }




}
