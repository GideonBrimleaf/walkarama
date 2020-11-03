package com.radiantchamber.walkarama.database.factories

import dev.alpas.ozone.EntityFactory
import dev.alpas.ozone.faker
import com.radiantchamber.walkarama.entities.Walk
import com.radiantchamber.walkarama.entities.Walks
import java.time.Instant
import java.util.concurrent.TimeUnit

internal object WalkFactory : EntityFactory<Walk, Walks>() {
    override val table = Walks
    
    override fun entity(): Walk {
        // https://alpas.dev/docs/ozone

        val randomDistance = faker.number().randomDouble(2, 1, 2000)

        return Walk {
            name = faker.name().name()
            totalDistance = randomDistance
            distanceLeftToTravel = randomDistance
            updatedAt = Instant.now()
            createdAt = faker.date().past(1, TimeUnit.HOURS).toInstant()
        }
    }
}