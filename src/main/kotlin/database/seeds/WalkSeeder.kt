package com.radiantchamber.walkarama.database.seeds

import com.radiantchamber.walkarama.database.factories.WalkFactory
import dev.alpas.Application
import dev.alpas.ozone.Seeder
import dev.alpas.ozone.from

internal class WalkSeeder : Seeder() {
    override fun run(app: Application) {
        val walks = from(WalkFactory, 1)
        // Run your seeder(s) here
        // val users = from(UserFactory, "name" to "Jane Doe")
        
        // https://alpas.dev/docs/ozone
    }
}