package com.sage.everyapart.mail.adaptor.out.gmail

import com.sage.everyapart.mail.domain.EmailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

@Component
class GmailAdaptor(
    private val javaMailSender: JavaMailSender,
) {

    fun sendMail(emailMessage: EmailMessage) {

        val mimeMessage = javaMailSender.createMimeMessage()

        MimeMessageHelper(mimeMessage, false, "UTF-8").let {
            it.setTo(emailMessage.to)
            it.setSubject(emailMessage.subject)
            it.setText(emailMessage.message, false)
        }

        javaMailSender.send(mimeMessage)

    }

}
