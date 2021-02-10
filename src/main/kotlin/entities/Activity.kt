package com.radiantchamber.walkarama.entities

import dev.alpas.ozone.*
import me.liuwj.ktorm.jackson.json
import me.liuwj.ktorm.schema.long
import me.liuwj.ktorm.schema.typeRef
import java.time.Instant

interface Activity : OzoneEntity<Activity> {
    var id: Long
    val user: User
    val walk: Walk
    val payload: Map<String, Any?>
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<Activity>()
}

object Activities : OzoneTable<Activity>("activities") {
    val id by bigIncrements()
    val userId by long("user_id").belongsTo(Users) {it.user}
    val walkId by long("walk_id").belongsTo(Walks) {it.walk}
    val payload by json("action", typeRef<Map<String, Any?>>()).bindTo { it.payload }
    val createdAt by createdAt()
    val updatedAt by updatedAt()
}