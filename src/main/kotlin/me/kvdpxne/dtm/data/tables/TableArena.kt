package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object TableArena : Table<Nothing>("arena") {

  val name = text("name")
  val mapIdentifier = text("map_identifier")
  val mapName = text("map_name")

  val identifier = text("identifier").primaryKey()
}