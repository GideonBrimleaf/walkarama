package com.radiantchamber.walkarama.middleware

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.Handler
import dev.alpas.Middleware
import dev.alpas.http.HttpCall
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.not
import me.liuwj.ktorm.entity.findOne

class AcceptWalkMiddleware : Middleware<HttpCall>() {
    override fun invoke(call: HttpCall, forward: Handler<HttpCall>) {
        val user = call.caller<User>()
        val pendingMembership =
            WalkMemberships.findOne { it.userId eq user.id and !it.accepted}

        if (pendingMembership != null) {
            call.redirect().toRouteNamed(
                "walks.membership_show",
                mapOf("id" to pendingMembership.walk.id, "member_id" to user.id))
        } else {
            forward(call)
        }
    }
}