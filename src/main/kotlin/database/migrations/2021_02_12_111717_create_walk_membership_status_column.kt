package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.WalkMemberships
import dev.alpas.ozone.migration.Migration

class CreateWalkMembershipStatusColumn : Migration() {
    override var name = "2021_02_12_111717_create_walk_membership_status_column"

    override fun shouldSkipBatch(batch: Int) = batch == 1
    
    override fun up() {
        modifyTable(WalkMemberships) {
             addColumn(WalkMemberships.accepted).after(WalkMemberships.walkId)
        }
    }
    
    override fun down() {
        modifyTable(WalkMemberships) {
            dropColumn("accepted")
        }
    }
}