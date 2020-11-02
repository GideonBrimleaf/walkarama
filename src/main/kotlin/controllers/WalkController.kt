package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.routing.Controller
import me.liuwj.ktorm.dsl.delete
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findAll

class WalkController : Controller() {
    fun index(call: HttpCall) {
        val allWalks = Walks.findAll()
        call.render("walks", "walks" to allWalks)
    }

    fun delete(call: HttpCall) {
        Walks.delete { it.id eq call.longParam("id").orAbort() }
        call.redirect().back()
    }
}