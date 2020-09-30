package com.radiantchamber.walkarama.controllers

import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.routing.Controller

class StuffController : Controller() {
    fun index(call: HttpCall) {
        println("I am a call!" + call.headers("User-Agent"))
        call.render("stuff")
    }

    fun findr(call:HttpCall) {
        val stuff = call.longParam("blah").orAbort()
        val schtuff = call.stringParam("someSchtuff").orAbort()
        call.render("findr", mapOf("thing" to stuff, "otherThing" to schtuff))
    }
}