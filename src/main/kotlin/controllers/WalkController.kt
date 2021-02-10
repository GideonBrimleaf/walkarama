package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.*
import dev.alpas.auth.middleware.VerifiedEmailOnlyMiddleware
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.ozone.findOrFail
import dev.alpas.routing.Controller
import dev.alpas.routing.ControllerMiddleware
import me.liuwj.ktorm.dsl.*
import java.time.Instant

class WalkController : Controller() {
    override fun middleware(call: HttpCall) = listOf(ControllerMiddleware(VerifiedEmailOnlyMiddleware::class))

    fun index(call: HttpCall) {
        val user = call.caller<User>()
        val walks = user.walks
        val memberships = user.memberships

        call.render("walks", mapOf("walks" to walks, "memberships" to memberships))
    }

    fun showActive(call: HttpCall) {
        val user = call.caller<User>()
        val activeWalk = user.walks.firstOrNull { it.isActive }
        val activeMembership = user.memberships.find { it.isActive }

        when {
            activeWalk != null -> call.render("walks_active", "walk" to activeWalk)
            activeMembership != null -> call.render("walks_active", "walk" to activeMembership)
            else -> call.redirect().toRouteNamed("walks.new")
        }
    }

    fun delete(call: HttpCall) {
        Walks.delete { it.id eq call.longParam("id").orAbort() }
        call.redirect().toRouteNamed("walks.list")
    }

    fun edit(call: HttpCall) {
        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)
        call.render("walk_edit", "walk" to foundWalk)
    }

    fun update(call: HttpCall) {
        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)

        foundWalk.name = call.jsonBody?.get("name").toString().orAbort()
        foundWalk.totalDistance = call.jsonBody?.get("distanceInMetres").orAbort() as Int
        foundWalk.distanceLeftToTravel = call.jsonBody?.get("distanceLeftToTravel").orAbort() as Int
        foundWalk.startPointLat = call.jsonBody?.get("startPointLat").orAbort() as Double
        foundWalk.startPointLong = call.jsonBody?.get("startPointLng").orAbort() as Double
        foundWalk.endPointLat = call.jsonBody?.get("endPointLat").orAbort() as Double
        foundWalk.endPointLong = call.jsonBody?.get("endPointLng").orAbort() as Double
        foundWalk.isActive = call.jsonBody?.get("isActive").orAbort() as Boolean
        foundWalk.updatedAt = Instant.now()

        foundWalk.flushChanges()
        call.acknowledge()
    }

    fun new(call:HttpCall) {
        val user = call.caller<User>()
        val activeWalk = user.walks.firstOrNull { it.isActive }
        val activeMembership = user.memberships.find { it.isActive }

        if (activeWalk != null || activeMembership != null) call.redirect().toRouteNamed("walks.show_active")
        else call.render("walk_new")
    }

    fun create(call:HttpCall) {
        val newWalk = Walks.create {
            it.name to call.jsonBody?.get("name")
            it.ownerId to call.user.id
            it.totalDistance to call.jsonBody?.get("distanceInMetres")
            it.distanceLeftToTravel to call.jsonBody?.get("distanceInMetres")
            it.startPointLat to call.jsonBody?.get("startPointLat")
            it.startPointLong to call.jsonBody?.get("startPointLng")
            it.endPointLat to call.jsonBody?.get("endPointLat")
            it.endPointLong to call.jsonBody?.get("endPointLng")
            it.isActive to true
        }
        logCreateWalkActivity(newWalk, mapOf("action" to "created walk", "name" to newWalk.name))

        call.acknowledge(201)
    }

    private fun logCreateWalkActivity(walk: Walk, payload: Map<String, Any?>) {
        val now = call.nowInCurrentTimezone().toInstant()
        val user = caller<User>()

        Activities.insert {
            it.payload to payload
            it.walkId to walk.id
            it.userId to user.id
            it.createdAt to now
            it.updatedAt to now
        }
    }

    fun reactivate(call: HttpCall) {
        val user = call.caller<User>()
        Walks.update {
            it.isActive to false
            where {
                Walks.ownerId eq user.id
            }
        }

        val activeMembership = user.memberships.find { it.isActive }

        if(activeMembership != null) {
            WalkMemberships.delete {
                it.userId eq user.id
                it.walkId eq activeMembership.id
            }
        }

        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)
        foundWalk.isActive = true
        foundWalk.flushChanges()

        call.redirect().toRouteNamed("walks.show_active")
    }
}