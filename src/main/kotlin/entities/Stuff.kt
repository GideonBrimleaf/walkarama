package com.radiantchamber.walkarama.entities

import dev.alpas.ozone.*
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.timestamp
import java.time.Instant

interface Stuff : OzoneEntity<Stuff> {
    var id: Long
    var name: String?
    var numbero: Int?
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<Stuff>()
}

object Stuffs : OzoneTable<Stuff>("stuffs") {
    val id by bigIncrements()
    val name by string("name").size(150).nullable().bindTo { it.name }
    val numbero by int("numbero").nullable().bindTo { it.numbero }
    val createdAt by timestamp("created_at").useCurrent().bindTo { it.createdAt }
    val updatedAt by timestamp("updated_at").useCurrent().bindTo { it.updatedAt }
}