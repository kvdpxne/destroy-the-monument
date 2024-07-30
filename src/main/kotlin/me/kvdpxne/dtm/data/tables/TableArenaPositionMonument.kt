package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text

object TableArenaPositionMonument : Table<Nothing>("arena_position_monument") {

  val arenaIdentifier = text("arena_identifier")
  val positionMonumentIdentifier = text("position_monument_identifier")
}