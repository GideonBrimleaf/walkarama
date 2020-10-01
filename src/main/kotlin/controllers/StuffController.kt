package com.radiantchamber.walkarama.controllers

import dev.alpas.http.HttpCall
import dev.alpas.orAbort
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
        call.render("findr", mapOf("thing" to stuff, "otherThing" to schtuff))
    }
}