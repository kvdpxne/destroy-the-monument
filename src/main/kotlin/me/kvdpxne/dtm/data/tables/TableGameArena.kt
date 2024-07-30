package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text

object TableGameArena : Table<Nothing>("game_arena") {

  val gameIdentifier = text("game_identifier")
  val arenaIdentifier = text("arena_identifier")
}