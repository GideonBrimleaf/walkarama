package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO Add navigation
//    TODO Make this actually look decent
//    TODO Add invites
//    TODO Show user progression along walk
//    TODO User can see latest activity from walk (like projects)
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
