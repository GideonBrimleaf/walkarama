package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO User should just see one map - if they have a walk the pins are in place, if not it will prompt them to create a map
//    TODO Update should have a warning window to extend/reduce the walk
//    TODO Delete should reset the map without pins
//    TODO Add authentication
//    TODO Each user should only have 1 walk
//    TODO Add invites
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
