package com.radiantchamber.walkarama.middleware

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.Handler
import dev.alpas.Middleware
import dev.alpas.http.HttpCall
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.entity.findList

class AcceptWalkMiddleware : Middleware<HttpCall>() {
    override fun invoke(call: HttpCall, forward: Handler<HttpCall>) {
        val user = call.caller<User>()
        val pendingMemberships =
            WalkMemberships.findList { it.userId eq user.id }.filter { !it.accepted }

        if (pendingMemberships.isNotEmpty()) {
            call.redirect().toRouteNamed("walks.show_active")
        } else {
            forward(call)
        }
    }
}