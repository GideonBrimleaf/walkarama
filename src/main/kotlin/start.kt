package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO Update should have a warning window to extend/reduce the walk
//    TODO Update should be in the same window as walk view
//    TODO Add authentication
//    TODO Each user should only have 1 walk
//    TODO Add invites
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
