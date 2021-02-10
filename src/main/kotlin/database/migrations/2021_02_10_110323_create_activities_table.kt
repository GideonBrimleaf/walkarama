package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.Activities
import dev.alpas.ozone.migration.Migration

class CreateActivitiesTable : Migration() {
    override var name = "2021_02_10_110323_create_activities_table"
    
    override fun up() {
        createTable(Activities)
    }
    
    override fun down() {
        dropTable(Activities)
    }
}