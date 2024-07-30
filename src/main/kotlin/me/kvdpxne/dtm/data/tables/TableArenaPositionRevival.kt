package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text

object TableArenaPositionRevival : Table<Nothing>("arena_position_revival") {

  val arenaIdentifier = text("arena_identifier")
  val positionRevivalIdentifier = text("position_revival_identifier")
}