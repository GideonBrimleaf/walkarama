package com.radiantchamber.walkarama.database.seeds

import com.radiantchamber.walkarama.database.factories.UserFactory
import com.radiantchamber.walkarama.database.factories.WalkFactory
import com.radiantchamber.walkarama.entities.Users
import dev.alpas.Application
import dev.alpas.make
import dev.alpas.orAbort
import dev.alpas.ozone.Seeder
import dev.alpas.ozone.from
import me.liuwj.ktorm.entity.findById

// https://alpas.dev/docs/seeding
internal class DatabaseSeeder : Seeder() {
    override fun run(app: Application) {
        val userMe = Users.findById(1).orAbort()
        val userRandom = from(UserFactory(app.make()))

        from(WalkFactory(), 2, mapOf("owner_id" to userRandom.id))
        from(WalkFactory(), 3, mapOf("owner_id" to userMe.id))

    }
}
