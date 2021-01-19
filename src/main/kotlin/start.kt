package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO All walks should have an active flag
//    TODO Each user should only have 1 active walk which is shown in walks
//    TODO User can see inactive walks
//    TODO Add invites
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
