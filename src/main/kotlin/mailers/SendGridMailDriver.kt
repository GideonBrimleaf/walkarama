package com.radiantchamber.walkarama.mailers

import com.sendgrid.*
import dev.alpas.Environment
import dev.alpas.mailing.MailMessage
import dev.alpas.mailing.drivers.MailDriver
import java.io.IOException

class SendGridMailDriver(private val env:Environment): MailDriver {
    override fun send(mail: MailMessage) {
        val connection = SendGrid(env("SEND_GRID_API_KEY"))
        val mailFrom = Email(env("MAIL_USERNAME"))
        val mailTo = Email(mail.to)
        val content = Content("text/plain", mail.message)
        val message = Mail(mailFrom, mail.subject, mailTo, content)
        val request = Request()

        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = message.build()
            val response = connection.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (ex:IOException) {
            throw ex
        }
    }
}