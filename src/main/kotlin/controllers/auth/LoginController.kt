package com.radiantchamber.walkarama.controllers.auth

import com.radiantchamber.walkarama.controllers.WalkController
import dev.alpas.auth.HandlesUserLogin
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

class LoginController : Controller(), HandlesUserLogin {
    override fun afterLoginRedirectTo(call: HttpCall) = route("walks.show_active")
}