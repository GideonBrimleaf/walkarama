package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.Walks
import dev.alpas.ozone.migration.Migration

class CreateWalksTable : Migration() {
    override var name = "2020_11_02_103722_create_walks_table"
    
    override fun up() {
        createTable(Walks)
    }
    
    override fun down() {
        dropTable(Walks)
    }
}