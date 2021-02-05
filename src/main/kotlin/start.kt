package com.radiantchamber.walkarama

import dev.alpas.Alpas
import java.io.File

fun main(args: Array<String>) {
//    TODO User should not be able to deactivate a walk they are a member of
//    TODO Users should be able to leave a walk they are a member of (set membership to inactive?)
//    TODO Reactivating one of your walks should remove you from any walks you're a member of
//    TODO Deactivating a current walk should archive it for both user and members (should already be done but check)
//    TODO User can see latest activity from walk (like projects)
//    TODO User can input estimated stride length for each step
//    TODO Make this actually look decent

//    TODO Walk show page for archived walks - show map and activity list
//    TODO Invite should be mailed out and accepted on request
//    TODO Show user progression along walk
    val file = File(".env")
    if (!file.exists()) {
        file.createNewFile()
    }
    return Alpas(args).routes { addRoutes() }.ignite()
}
