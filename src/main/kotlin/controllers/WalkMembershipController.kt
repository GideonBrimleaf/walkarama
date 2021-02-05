package com.radiantchamber.walkarama.controllers

import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

class WalkMembershipController : Controller() {
    fun add(call: HttpCall) {
        call.reply("Hello, WalkMembershipController!")
    }
}