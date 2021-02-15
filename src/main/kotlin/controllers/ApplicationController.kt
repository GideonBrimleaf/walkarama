package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.middleware.AcceptWalkMiddleware
import dev.alpas.auth.middleware.VerifiedEmailOnlyMiddleware
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller
import dev.alpas.routing.ControllerMiddleware

open class ApplicationController : Controller() {
    override fun middleware(call: HttpCall) = listOf(
        ControllerMiddleware(VerifiedEmailOnlyMiddleware::class),
        ControllerMiddleware(AcceptWalkMiddleware::class)
    )
}