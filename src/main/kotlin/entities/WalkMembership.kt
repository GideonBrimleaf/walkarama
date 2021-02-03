package com.radiantchamber.walkarama.entities

import com.radiantchamber.walkarama.entities.Walks.bindTo
import com.radiantchamber.walkarama.entities.Walks.useCurrent
import dev.alpas.ozone.*
import me.liuwj.ktorm.schema.long
import me.liuwj.ktorm.schema.timestamp
import java.time.Instant

interface WalkMembership : OzoneEntity<WalkMembership> {
    var id: Long
    val member: User
    val walk: Walk
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<WalkMembership>()
}

object WalkMemberships : OzoneTable<WalkMembership>("walk_memberships") {
    val id by bigIncrements()
    val userId by long("user_id").belongsTo(Users) { it.member }
    val walkId by long("walk_id").belongsTo(Walks) { it.walk }
    val createdAt by timestamp("created_at").useCurrent().bindTo { it.createdAt }
    val updatedAt by timestamp("updated_at").useCurrent().bindTo { it.updatedAt }
}