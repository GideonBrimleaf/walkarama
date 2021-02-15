package com.radiantchamber.walkarama

import com.radiantchamber.walkarama.controllers.*
import dev.alpas.auth.authRoutes
import dev.alpas.routing.RouteGroup
import dev.alpas.routing.Router

// https://alpas.dev/docs/routing
fun Router.addRoutes() = apply {
    group {
        webRoutesGroup()
        authRoutes()
    }.middlewareGroup("web")

    apiRoutes()
}

private fun RouteGroup.webRoutesGroup() {
    get("/", WelcomeController::index).name("welcome")
    // register more web routes here
    get("/stuff", StuffController::index)
    get("/stuff/new", StuffController::new).name("new")
    post("/stuff", StuffController::create).name("create")
    delete("/stuff/<id>", StuffController::delete).name("delete")
    group("/walks") {
        addWalksRoutes()
    }.name("walks").mustBeAuthenticated()
    group("/users") {
        addUserRoutes()
    }.name("users").mustBeAuthenticated()
}

private fun RouteGroup.addWalksRoutes() {
    get("/", WalkController::index).name("list")
    get("/current", WalkController::showActive).name("show_active")
    get("/new", WalkController::new).name("new")
    post("/", WalkController::create).name("create")
    get("/<id>/edit", WalkController::edit).name("edit")
    delete("/<id>", WalkController::delete).name("delete")
    patch("/<id>", WalkController::update).name("update")
    patch("/<id>/reactivate", WalkController::reactivate).name("reactivate")
    post("/<id>/membership", WalkMembershipController::add).name("membership_add")
    get("/<id>/membership/<member_id>", WalkMembershipController::show).name("membership_show")
    delete("/<id>/membership/<member_id>", WalkMembershipController::delete).name("membership_remove")
}

private fun RouteGroup.addUserRoutes() {
    get("/<id>", UserController::show).name("show")
    get("/<id>/edit", UserController::edit).name("edit")
    patch("/<id>", UserController::update).name("update")
}

private fun Router.apiRoutes() {
    // register API routes here
}
