package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO Create walk should update the details
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
