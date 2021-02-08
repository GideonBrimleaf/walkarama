package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Users
import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findById
import me.liuwj.ktorm.entity.findOne

class WalkMembershipController : Controller() {
    fun add(call: HttpCall) {
        val now = call.nowInCurrentTimezone().toInstant()
        val email = call.stringParam("email").orAbort()
        val invitee = Users.findOne {it.email eq email}.orAbort()

       WalkMemberships.create {
            it.walkId to call.longParam("id")
            it.userId to invitee.id
            it.createdAt to now
            it.updatedAt to now
        }

        val inviteeActiveWalk = invitee.walks.find { it.isActive }
        if (inviteeActiveWalk != null) {
            inviteeActiveWalk.isActive = false
            inviteeActiveWalk.flushChanges()
        }

        flash("success", "${invitee.name} <${invitee.email}> has joined your walk")
        call.redirect().back()
    }

    fun delete(call: HttpCall) {
        val memberId = call.longParam("member_id").orAbort()
        val walkId = call.longParam("id").orAbort()
        val member = Users.findById(memberId)

        WalkMemberships.delete {
            it.walkId eq walkId
            it.userId eq memberId
        }

        flash("success", "${member?.name} <${member?.email}> has been removed from the walk")
        call.redirect().back()
    }
}