package com.radiantchamber.walkarama.database.factories

import com.radiantchamber.walkarama.entities.Walk
import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.ozone.EntityFactory
import dev.alpas.ozone.faker
import java.time.Instant
import java.util.concurrent.TimeUnit

internal class WalkFactory : EntityFactory<Walk, Walks>() {
    override val table = Walks
    
    override fun entity(): Walk {
        // https://alpas.dev/docs/ozone

        val randomDistance = 2929.02

        return Walk {
            name = faker.funnyName().name()
            totalDistance = randomDistance
            distanceLeftToTravel = randomDistance
            startPointLat = 51.50741538310507
            startPointLong = -0.06362994689941726
            endPointLat = 51.523065389149096
            endPointLong = -0.097618899536136
            isActive = false
            updatedAt = Instant.now()
            createdAt = faker.date().past(1, TimeUnit.HOURS).toInstant()
        }
    }
}