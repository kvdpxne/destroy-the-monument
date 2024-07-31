package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.text

object TableGameTeam : Table<Nothing>("game_team") {

  val gameIdentifier = text("game_identifier")
  val teamIdentifier = text("team_identifier")
}