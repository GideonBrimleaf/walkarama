package com.radiantchamber.walkarama.controllers

import com.radiantchamber.walkarama.entities.Activities
import com.radiantchamber.walkarama.entities.User
import com.radiantchamber.walkarama.entities.Walk
import dev.alpas.http.HttpCall
import me.liuwj.ktorm.dsl.insert

interface CanLogWalkActivity {

    fun logWalkActivity(walk: Walk, payload: Map<String, Any?>, call:HttpCall, user:User?=null) {
        val now = call.nowInCurrentTimezone().toInstant()
        val userToLog = user ?: call.caller()

        Activities.insert {
            it.payload to payload
            it.walkId to walk.id
            it.userId to userToLog.id
            it.createdAt to now
            it.updatedAt to now
        }
    }
}