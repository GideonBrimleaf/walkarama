package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller
import me.liuwj.ktorm.entity.findAll

class WalkController : Controller() {
    fun index(call: HttpCall) {
        val allWalks = Walks.findAll()
        call.render("walks", "walks" to allWalks)
    }
}