package com.radiantchamber.walkarama.entities

import dev.alpas.ozone.*
import java.time.Instant

interface Stuff : OzoneEntity<Stuff> {
    var id: Long
    var name: String?
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<Stuff>()
}

object Stuffs : OzoneTable<Stuff>("stuffs") {
    val id by bigIncrements()
    val name by string("name").size(150).nullable().bindTo { it.name }
    val createdAt by createdAt()
    val updatedAt by updatedAt()
}