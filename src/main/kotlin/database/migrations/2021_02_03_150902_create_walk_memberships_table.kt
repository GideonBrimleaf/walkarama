package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.ozone.migration.Migration

class CreateWalkMembershipsTable : Migration() {
    override var name = "2021_02_03_150902_create_walk_memberships_table"
    
    override fun up() {
        createTable(WalkMemberships)
    }
    
    override fun down() {
        dropTable(WalkMemberships)
    }
}