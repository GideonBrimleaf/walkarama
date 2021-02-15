package com.radiantchamber.walkarama.rules

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Users
import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.validation.ErrorMessage
import dev.alpas.validation.Rule
import dev.alpas.validation.ValidationGuard
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findOne


class ValidUserRule(private val message: ErrorMessage = null) : Rule() {
    var user: User? = null
        private set
    override fun check(attribute: String, call: HttpCall): Boolean {
        val email = call.stringParam("email").orAbort()
        if (email == call.caller<User>().email) {
            error = "This is your walk, you cannot invite yourself to join it"
            return false
        }

//      TODO Can this be refactored to Ozone class Users.findOne with mapOf()?
        user = Users.findOne {it.email eq email}
        if (user == null) {
            error = "The user must be registered on ${call.env("APP_NAME")} to be invited to the walk"
            return false
        }

        val walkId = call.longParam("id").orAbort()
        val membership = WalkMemberships.findOne { (it.userId eq user!!.id) and (it.walkId eq walkId) }
        if (membership != null) {
            error = "The user is already on this walk with you"
            return false
        }

        return true
    }
}

fun ValidationGuard.validUserRule(message: ErrorMessage = null): Rule {
    return rule(ValidUserRule(message))
}