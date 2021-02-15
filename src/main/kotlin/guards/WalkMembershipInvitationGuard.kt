package com.radiantchamber.walkarama.guards

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.WalkMemberships
import com.radiantchamber.walkarama.rules.ValidUserRule
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.validation.Rule
import dev.alpas.validation.ValidationGuard
import dev.alpas.validation.email
import dev.alpas.validation.required

class WalkMembershipInvitationGuard : ValidationGuard() {
    private val validUserRule by lazy { ValidUserRule() }

    override fun rules(): Map<String, Iterable<Rule>> {
          return mapOf("email" to listOf(required(), email(), validUserRule))
    }

    fun createMembership(): User {
        val now = call.nowInCurrentTimezone().toInstant()
        val member = validUserRule.user.orAbort()

        WalkMemberships.create {
            it.walkId to call.longParam("id")
            it.userId to member.id
            it.createdAt to now
            it.updatedAt to now
        }

        return member
    }
}