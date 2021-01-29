package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO Add invites
//    TODO User can see latest activity from walk (like projects)
//    TODO Make this actually look decent
//    TODO Show user progression along walk
//    TODO Walk show page for archived walks - show map and activity list
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
