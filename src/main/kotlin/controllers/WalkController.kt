package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.findOrFail
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.minus
import me.liuwj.ktorm.dsl.update
import me.liuwj.ktorm.entity.findAll
import java.time.Instant

class WalkController : Controller() {
    fun index(call: HttpCall) {
        val allWalks = Walks.findAll()
        call.render("walks", "walks" to allWalks)
    }

    fun delete(call: HttpCall) {
        Walks.delete { it.id eq call.longParam("id").orAbort() }
        call.redirect().back()
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

    fun new(call:HttpCall){
        call.render("walk_new")
    }
}