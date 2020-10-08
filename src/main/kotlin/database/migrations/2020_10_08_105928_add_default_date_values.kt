package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.Stuff
import com.radiantchamber.walkarama.entities.Stuffs
import dev.alpas.ozone.migration.Migration

class AddDefaultDateValues : Migration() {
    override var name = "2020_10_08_105928_add_default_date_values"
    
    override fun up() {
        val createQuery = "UPDATE stuffs SET created_at = NOW()"
        execute(createQuery)
    }
    
    override fun down() {
        val createQuery = "UPDATE stuffs SET created_at = NULL"
        execute(createQuery)
    }
}