package com.radiantchamber.walkarama.entities

import dev.alpas.auth.BaseUser
import dev.alpas.auth.BaseUsersTable
import dev.alpas.ozone.OzoneEntity
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findList
import me.liuwj.ktorm.entity.findOne

// https://alpas.dev/docs/ozone#dao
interface User : BaseUser<User> {
    // https://alpas.dev/docs/email-verification
    override val mustVerifyEmail get() = true

    val walks get() = Walks.findList { it.ownerId eq id }
    val memberships get() = WalkMemberships.findList { it.userId eq id }.map { it.walk }

    companion object : OzoneEntity.Of<User>()
}

// https://alpas.dev/docs/ozone#dsl
object Users : BaseUsersTable<User>()
