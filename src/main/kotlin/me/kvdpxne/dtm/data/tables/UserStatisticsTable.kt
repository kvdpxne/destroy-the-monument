package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserStatisticsTable : Table("user_statistics") {

  //
  val identifier: Column<UUID> = this.uuid("identifier").uniqueIndex()

  //
  val kills: Column<Int> = this.integer("kills").default(0)
  val assists: Column<Int> = this.integer("assists").default(0)
  val deaths: Column<Int> = this.integer("deaths").default(0)
  val destroyedMonuments: Column<Int> = this.integer("destroyed_monuments").default(0)
  val playedGames: Column<Int> = this.integer("played_games").default(0)
  val gamesWon: Column<Int> = this.integer("games_won").default(0)
  val gamesLost: Column<Int> = this.integer("games_lost").default(0)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}