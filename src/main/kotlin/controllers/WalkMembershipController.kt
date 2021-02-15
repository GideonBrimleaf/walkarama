package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Users
import com.radiantchamber.walkarama.entities.WalkMemberships
import com.radiantchamber.walkarama.entities.Walks
import com.radiantchamber.walkarama.guards.WalkMembershipInvitationGuard
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.ozone.findOrFail
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.and
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.not
import me.liuwj.ktorm.entity.findById
import me.liuwj.ktorm.entity.findOne

class WalkMembershipController : Controller(), CanLogWalkActivity {
    fun add(call: HttpCall) {
        val foundWalk = Walks.findById(call.longParam("id").orAbort()).orAbort()
        call.validateUsing(WalkMembershipInvitationGuard::class) {
            val invitee = createMembership()
            logWalkActivity(foundWalk, mapOf("action" to "invited to walk", "name" to foundWalk.name), call, invitee)
            call.redirect().back()
        }
    }

    fun show(call: HttpCall) {
        val user = call.caller<User>()
        val walkId = call.longParam("id").orAbort()
        val foundWalk = user.memberships.find { it.id == walkId }.orAbort()

        call.render("membership_show", mapOf("walk" to foundWalk))
    }

    fun accept(call:HttpCall) {
        val user = call.caller<User>()
        val walkId = call.longParam("id").orAbort()
        val invite = WalkMemberships.findOne { (it.walkId eq walkId) and (it.userId eq user.id) }.orAbort()
        val invitedWalk = Walks.findById(walkId).orAbort()

        invite.accepted = true
        invite.updatedAt = call.nowInCurrentTimezone().toInstant()
        invite.flushChanges()

        val activeWalk = user.walks.find { it.isActive }
        if (activeWalk != null) {
            activeWalk.isActive = false
            activeWalk.flushChanges()
        }

        logWalkActivity(invitedWalk, mapOf("action" to "joined walk", "name" to invitedWalk.name), call)
        call.redirect().toRouteNamed("walks.show_active")
    }

    fun delete(call: HttpCall) {
        val walk = Walks.findOrFail(call.longParam("id").orAbort())
        val member = Users.findOrFail(call.longParam("member_id").orAbort())

        WalkMemberships.delete {
            it.walkId eq walk.id
            it.userId eq member.id
        }

        logWalkActivity(walk, mapOf("action" to "left walk", "name" to walk.name), call, member)
        flash("success", "${member.name} <${member.email}> has been removed from the walk")
        call.redirect().toRouteNamed("walks.show_active")
    }
}