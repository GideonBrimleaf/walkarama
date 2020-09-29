package com.radiantchamber.walkarama.controllers

import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

class StuffController : Controller() {
    fun index(call: HttpCall) {
        println("I am a call!" + call.headers("User-Agent"))
        call.render("stuff")
    }
}