package com.radiantchamber.walkarama.database.seeds

import com.radiantchamber.walkarama.database.factories.UserFactory
import dev.alpas.Application
import dev.alpas.make
import dev.alpas.ozone.Seeder
import dev.alpas.ozone.from

// https://alpas.dev/docs/seeding
internal class DatabaseSeeder : Seeder() {
    override fun run(app: Application) {
        from(UserFactory(app.make()),2)

        WalksSeeder().run(app)
    }
}
