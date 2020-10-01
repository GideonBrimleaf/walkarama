package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.Stuffs
import dev.alpas.ozone.migration.Migration

class CreateStuffsTable : Migration() {
    override var name = "2020_10_01_141502_create_stuffs_table"
    
    override fun up() {
        createTable(Stuffs)
    }
    
    override fun down() {
        dropTable(Stuffs)
    }
}