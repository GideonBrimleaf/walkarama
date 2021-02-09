package com.radiantchamber.walkarama.database.seeds

import com.radiantchamber.walkarama.database.factories.WalkFactory
import com.radiantchamber.walkarama.entities.Users
import dev.alpas.Application
import dev.alpas.ozone.Seeder
import dev.alpas.ozone.from
import me.liuwj.ktorm.entity.findAll

internal class WalksSeeder : Seeder() {
    override fun run(app: Application) {
        val randomUsers = Users.findAll()

        from(WalkFactory(), 2, mapOf("owner_id" to randomUsers.first().id))
        from(WalkFactory(), 3, mapOf("owner_id" to randomUsers.last().id))
    }
}