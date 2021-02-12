package com.radiantchamber.walkarama.controllers

import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

// https://alpas.dev/docs/controllers
class WelcomeController : Controller() {
    fun index(call: HttpCall) {
        if (auth().user != null) call.redirect().toRouteNamed("walks.show_active")
        else call.render("welcome")
    }
}
