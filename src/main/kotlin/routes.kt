package com.radiantchamber.walkarama

import com.radiantchamber.walkarama.controllers.StuffController
import com.radiantchamber.walkarama.controllers.WalkController
import com.radiantchamber.walkarama.controllers.WelcomeController
import dev.alpas.routing.RouteGroup
import dev.alpas.routing.Router

// https://alpas.dev/docs/routing
fun Router.addRoutes() = apply {
    group {
        webRoutesGroup()
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
    }.name("walks")
}

private fun RouteGroup.addWalksRoutes() {
    get("/", WalkController::index).name("list")
    get("/new", WalkController::new).name("new")
    post("/", WalkController::create).name("create")
    get("/<id>/edit", WalkController::edit).name("edit")
    delete("/<id>", WalkController::delete).name("delete")
    patch("/<id>", WalkController::update).name("update")
}

private fun Router.apiRoutes() {
    // register API routes here
}
