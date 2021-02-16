package com.radiantchamber.walkarama.controllers.auth

import dev.alpas.auth.HandlesEmailVerification
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller

class EmailVerificationController : Controller(), HandlesEmailVerification {
    override fun ifVerifiedRedirectTo(call: HttpCall): String = "/walks/current"
}