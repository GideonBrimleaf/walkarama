package com.radiantchamber.walkarama.controllers

import dev.alpas.auth.middleware.VerifiedEmailOnlyMiddleware
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller
import dev.alpas.routing.ControllerMiddleware

class HomeController : ApplicationController() {
    fun index(call: HttpCall) {
        call.render("home")
    }
}