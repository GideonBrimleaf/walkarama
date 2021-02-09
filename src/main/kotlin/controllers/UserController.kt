package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Users
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.routing.Controller
import me.liuwj.ktorm.entity.findById

class UserController : Controller() {
    fun show(call: HttpCall) {
        val user = call.caller<User>()
        call.render("user_show", "user" to user)
    }

    fun edit(call:HttpCall) {
        val user = call.caller<User>()
        call.render("user_edit", "user" to user)
    }
}