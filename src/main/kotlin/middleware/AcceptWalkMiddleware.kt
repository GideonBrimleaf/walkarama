package com.radiantchamber.walkarama.middleware

import com.radiantchamber.walkarama.entities.User
import dev.alpas.Handler
import dev.alpas.Middleware
import dev.alpas.http.HttpCall

class AcceptWalkMiddleware : Middleware<HttpCall>() {
    override fun invoke(call: HttpCall, forward: Handler<HttpCall>) {
        val user = call.caller<User>()
        forward(call)
    }
}