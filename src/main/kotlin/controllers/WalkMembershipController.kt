package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Users
import com.radiantchamber.walkarama.entities.WalkMemberships
import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.not
import me.liuwj.ktorm.entity.findById
import me.liuwj.ktorm.entity.findOne

class WalkMembershipController : Controller(), CanLogWalkActivity {
    fun add(call: HttpCall) {
        val now = call.nowInCurrentTimezone().toInstant()
        val email = call.stringParam("email").orAbort()
        val walk = Walks.findById(call.longParam("id").orAbort()).orAbort()
        val invitee = Users.findOne {it.email eq email}.orAbort()

       WalkMemberships.create {
            it.walkId to walk.id
            it.userId to invitee.id
            it.createdAt to now
            it.updatedAt to now
        }

        val activeWalk = invitee.walks.find { it.isActive }
        if (activeWalk != null) {
            activeWalk.isActive = false
            activeWalk.flushChanges()
        }

        logWalkActivity(walk, mapOf("action" to "invited to walk", "name" to walk.name), call, invitee)
        flash("success", "${invitee.name} <${invitee.email}> has joined your walk")
        call.redirect().back()
    }

    fun show(call: HttpCall) {
        val user = call.caller<User>()
        val walkId = call.longParam("id").orAbort()
        val foundWalk = user.memberships.find { it.id == walkId }.orAbort()

        call.render("membership_show", mapOf("walk" to foundWalk))
    }

    fun accept(call:HttpCall) {
        val user = call.caller<User>()
        val invite = WalkMemberships.findOne { it.id eq call.longParam("id").orAbort() }.orAbort()
        val invitedWalk = Walks.findOne { it.id eq invite.walk.id }.orAbort()

        if (user.id == invite.member.id) {
            invite.accepted = true
            invite.updatedAt = call.nowInCurrentTimezone().toInstant()
            invite.flushChanges()
        }

        logWalkActivity(invitedWalk, mapOf("action" to "joined walk", "name" to invitedWalk.name), call)
        call.redirect().toRouteNamed("walks.show_active")
    }

    fun delete(call: HttpCall) {
        val walk = Walks.findById(call.longParam("id").orAbort()).orAbort()
        val member = Users.findById(call.longParam("member_id").orAbort()).orAbort()

        WalkMemberships.delete {
            it.walkId eq walk.id
            it.userId eq member.id
        }

        logWalkActivity(walk, mapOf("action" to "left walk", "name" to walk.name), call, member)
        flash("success", "${member.name} <${member.email}> has been removed from the walk")
        call.redirect().back()
    }
}