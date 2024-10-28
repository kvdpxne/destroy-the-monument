package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object UserStatisticsTable : Table(En.USER_STATISTICS) {

  //
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER).uniqueIndex()

  //
  val kills: Column<Int> = this.integer(Efn.KILLS).default(0)
  val assists: Column<Int> = this.integer(Efn.ASSISTS).default(0)
  val deaths: Column<Int> = this.integer(Efn.DEATHS).default(0)
  val destroyedMonuments: Column<Int> = this.integer(Efn.DESTROYED_MONUMENTS).default(0)
  val playedGames: Column<Int> = this.integer(Efn.PLAYED_GAMES).default(0)
  val gamesWon: Column<Int> = this.integer(Efn.GAMES_WON).default(0)
  val gamesLost: Column<Int> = this.integer(Efn.GAMES_LOST).default(0)

  //
  override val primaryKey: PrimaryKey = PrimaryKey(this.identifier)
}