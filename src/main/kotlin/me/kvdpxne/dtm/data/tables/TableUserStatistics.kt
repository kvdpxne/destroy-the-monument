package me.kvdpxne.dtm.data.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TableUserStatistics : Table<Nothing>("user_statistics") {

  val kills = int("kills")
  val assists = int("assists")
  val deaths = int("deaths")
  val destroyedMonuments = int("destroyed_monuments")
  val playedGames = int("played_games")
  val gamesWon = int("games_won")
  val gamesLost = int("games_lost")

  val identifier = varchar("identifier").primaryKey()
}