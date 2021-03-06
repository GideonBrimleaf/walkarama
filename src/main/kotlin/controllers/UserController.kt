package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Users
import com.radiantchamber.walkarama.middleware.AcceptWalkMiddleware
import dev.alpas.auth.middleware.VerifiedEmailOnlyMiddleware
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.routing.Controller
import dev.alpas.routing.ControllerMiddleware
import me.liuwj.ktorm.entity.findById

class UserController : ApplicationController() {
    fun show(call: HttpCall) {
        val user = call.caller<User>()
        call.render("user_show", "user" to user)
    }

    fun edit(call:HttpCall) {
        val user = call.caller<User>()
        call.render("user_edit", "user" to user)
    }

    fun update(call: HttpCall) {
        val user = call.caller<User>()

        user.strideLength = call.intParam("stride_length").orAbort() * 100
        user.flushChanges()

        call.redirect().toRouteNamed("users.show", mapOf("id" to user.id))
    }
}