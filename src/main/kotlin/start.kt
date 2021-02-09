package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO User can input estimated stride length for each ste
//    TODO User can see latest activity from walk (like projects)
//    TODO If invited - user should be redirected on all routes to a view to accept the invite
//    TODO Make this actually look decent - progression bar for walk
//    TODO Walk show page for archived walks - show map and activity list
//    TODO Either try mailer services or bin email verification

//    TODO Invite should be mailed out and accepted on request, this should show as pending for the inviter
//    TODO Show user progression along walk in map
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
