package com.radiantchamber.walkarama.entities

import dev.alpas.ozone.*
import me.liuwj.ktorm.schema.*
import java.time.Instant

interface Walk : OzoneEntity<Walk> {
    var id: Long
    var name: String?
    var owner: User
    var totalDistance: Int
    var distanceLeftToTravel: Int
    var startPointLat: Double
    var startPointLong: Double
    var endPointLat: Double
    var endPointLong: Double
    var isActive: Boolean
    val members get() = hasMany(WalkMemberships).map { it.member }
    val activities get() = hasMany(Activities)
    var createdAt: Instant?
    var updatedAt: Instant?

    companion object : OzoneEntity.Of<Walk>()
}

object Walks : OzoneTable<Walk>("walks") {
    val id by bigIncrements()
    val name by string("name").size(150).nullable().bindTo { it.name }
    val ownerId by long("owner_id").belongsTo(Users) { it.owner }
    val totalDistance by int("total_distance").nullable().bindTo { it.totalDistance }
    val distanceLeftToTravel by int("distance_left_to_travel").nullable().bindTo { it.distanceLeftToTravel }
    val startPointLat by double("start_point_lat").nullable().bindTo { it.startPointLat }
    val startPointLong by double("start_point_long").nullable().bindTo { it.startPointLong }
    val endPointLat by double("end_point_lat").nullable().bindTo { it.endPointLat }
    val endPointLong by double("end_point_long").nullable().bindTo { it.endPointLong }
    val isActive by boolean("is_active").bindTo { it.isActive }
    val createdAt by timestamp("created_at").useCurrent().bindTo { it.createdAt }
    val updatedAt by timestamp("updated_at").useCurrent().bindTo { it.updatedAt }
}