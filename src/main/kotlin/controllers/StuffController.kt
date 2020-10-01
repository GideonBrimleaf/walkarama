package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Stuff
import com.radiantchamber.walkarama.entities.Stuffs
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.ozone.create
import dev.alpas.routing.Controller

class StuffController : Controller() {
    fun index(call: HttpCall) {
        println("I am a call!" + call.headers("User-Agent"))
        call.render("stuff")
    }

    fun new(call: HttpCall) {
        println("I am a call!" + call.headers("User-Agent"))
        call.render("new_stuff")
    }

    fun create(call:HttpCall) {
        val stuff = call.longParam("numbero").orAbort()
        val schtuff = call.stringParam("inputero").orAbort()
        val thingy = Stuffs.create {
            it.numbero to stuff
            it.name to schtuff
        }
        call.render("findr", mapOf("thing" to thingy.numbero, "otherThing" to thingy.name))
    }
}