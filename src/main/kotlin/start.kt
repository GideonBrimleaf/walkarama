package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO Either try mailer services or bin email verification
//    TODO Remove redirect to welcome page on email verification - dependent on above
//    TODO Make this actually look decent - progression bar for walk, sort out flash messages

//    TODO Walk show page for archived walks - show map and activity list
//    TODO Invite should be mailed out and accepted on request, this should show as pending for the inviter
//    TODO Show user progression along walk in map
//    TODO Unit Testing
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
