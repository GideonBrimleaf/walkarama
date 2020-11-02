package com.radiantchamber.walkarama.entities

import com.radiantchamber.walkarama.entities.Stuffs.bindTo
import com.radiantchamber.walkarama.entities.Stuffs.useCurrent
import dev.alpas.ozone.*
import me.liuwj.ktorm.schema.double
import me.liuwj.ktorm.schema.float
import me.liuwj.ktorm.schema.timestamp
import java.time.Instant

interface Walk : OzoneEntity<Walk> {
    var id: Long
    var name: String?
    var distance: Double
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<Walk>()
}

object Walks : OzoneTable<Walk>("walks") {
    val id by bigIncrements()
    val name by string("name").size(150).nullable().bindTo { it.name }
    val distance by double("distance").nullable().bindTo { it.distance }
    val createdAt by timestamp("created_at").useCurrent().bindTo { it.createdAt }
    val updatedAt by timestamp("updated_at").useCurrent().bindTo { it.updatedAt }
}