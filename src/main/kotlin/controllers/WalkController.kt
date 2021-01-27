package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Walks
import com.radiantchamber.walkarama.entities.Walks.endPointLat
import com.radiantchamber.walkarama.entities.Walks.startPointLong
import dev.alpas.auth.middleware.VerifiedEmailOnlyMiddleware
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.ozone.findOneOrFail
import dev.alpas.ozone.findOrFail
import dev.alpas.routing.Controller
import dev.alpas.routing.ControllerMiddleware
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.entity.findOne
import java.time.Instant

class WalkController : Controller() {
    override fun middleware(call: HttpCall) = listOf(ControllerMiddleware(VerifiedEmailOnlyMiddleware::class))

    fun index(call: HttpCall) {
        val user = call.caller<User>()
        val walks = user.walks
        call.render("walks", "walks" to walks)
    }

    fun showActive(call: HttpCall) {
        val user = call.caller<User>()

        val activeWalk = user.walks.firstOrNull { it.isActive }

        if (activeWalk != null) {
            call.render("walks_active", "walk" to activeWalk)
        } else {
            call.redirect().toRouteNamed("walks.new")
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
        foundWalk.totalDistance = call.jsonBody?.get("distanceInMetres").orAbort() as Double
        foundWalk.distanceLeftToTravel = (call.jsonBody?.get("distanceLeftToTravel").orAbort() as Int).toDouble()
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

        if (activeWalk != null) {
            call.redirect().toRouteNamed("walks.show_active")
        } else {
            call.render("walk_new")
        }
    }

    fun create(call:HttpCall) {
        Walks.create {
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

        call.acknowledge(201)
    }

    fun reactivate(call:HttpCall) {
        val user = call.caller<User>()
        Walks.update {
            it.isActive to false
            where {
                Walks.ownerId eq user.id
            }
        }

        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)
        foundWalk.isActive = true
        foundWalk.flushChanges()

        call.redirect().toRouteNamed("walks.show_active")
    }
}