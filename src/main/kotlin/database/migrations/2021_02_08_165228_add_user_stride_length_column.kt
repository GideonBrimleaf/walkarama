package com.radiantchamber.walkarama.database.migrations

import com.radiantchamber.walkarama.entities.Users
import dev.alpas.ozone.migration.Migration

class AddUserStrideLengthColumn : Migration() {
    override var name = "2021_02_08_165228_add_user_stride_length_column"
    
    override fun up() {
        modifyTable(Users) {
            addColumn(Users.strideLength).after(Users.email)
        }
    }
    
    override fun down() {
        modifyTable(Users) {
             dropColumn("stride_length")
        }
    }
}