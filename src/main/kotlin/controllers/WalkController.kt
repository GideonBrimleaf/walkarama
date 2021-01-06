package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Walks
import com.radiantchamber.walkarama.entities.Walks.endPointLat
import com.radiantchamber.walkarama.entities.Walks.startPointLong
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.ozone.findOneOrFail
import dev.alpas.ozone.findOrFail
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.entity.findOne
import java.time.Instant

class WalkController : Controller() {
    fun index(call: HttpCall) {
        val latestWalk = Walks.select(Walks.createdAt).orderBy(Walks.createdAt.desc()).firstOrNull()

        if (latestWalk != null) {
            val foundWalk = Walks.findOne {
                it.createdAt eq latestWalk[Walks.createdAt]!!
            }
            call.render("walks", "walk" to foundWalk)
        } else {
            call.redirect().toRouteNamed("walks.new")
        }
    }

    fun delete(call: HttpCall) {
        Walks.delete { it.id eq call.longParam("id").orAbort() }
        call.redirect().toRouteNamed("walks.new")
    }

    fun edit(call: HttpCall) {
        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)
        call.render("walk_edit", "walk" to foundWalk)
    }

    fun update(call: HttpCall) {
        val id = call.longParam("id").orAbort()
        val foundWalk = Walks.findOrFail(id)
        val distancedTravelled = call.stringParam("walk-travelled").orAbort().toDouble()

        foundWalk.name = call.stringParam("walk-name")
        foundWalk.totalDistance = call.stringParam("walk-distance").orAbort().toDouble()
        if (distancedTravelled <= foundWalk.distanceLeftToTravel) {
            foundWalk.distanceLeftToTravel -= distancedTravelled
        } else {
            foundWalk.distanceLeftToTravel = 0.0
        }

        foundWalk.updatedAt = Instant.now()

        foundWalk.flushChanges()

        call.redirect().toRouteNamed("walks.list")
    }

    fun new(call:HttpCall) {
        if (Walks.count() > 0) {
            call.redirect().toRouteNamed("walks.list")
        }

        call.render("walk_new")
    }

    fun create(call:HttpCall) {
        Walks.create {
            it.name to call.jsonBody?.get("name")
            it.totalDistance to call.jsonBody?.get("distanceInMetres")
            it.distanceLeftToTravel to call.jsonBody?.get("distanceInMetres")
            it.startPointLat to call.jsonBody?.get("startPointLat")
            it.startPointLong to call.jsonBody?.get("startPointLng")
            it.endPointLat to call.jsonBody?.get("endPointLat")
            it.endPointLong to call.jsonBody?.get("endPointLng")
        }

        call.acknowledge(201)
    }
}