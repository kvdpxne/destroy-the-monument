package me.kvdpxne.dtm.data.tables

import me.kvdpxne.dtm.data.ArenaTable.primaryKey
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object TableArena : Table<Nothing>("arena") {

  val name = varchar("name")
  val worldIdentifier = varchar("world_identifier")
  val worldName = varchar("world_name")

  val identifier = varchar("identifier").primaryKey()
}