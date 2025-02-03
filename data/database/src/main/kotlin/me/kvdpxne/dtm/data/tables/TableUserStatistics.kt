package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.extensions.usint32
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableUserStatistics : Table(En.USER_STATISTICS) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex(En.USER_STATISTICS)

  /**
   * @since 0.1.0
   */
  val kills: Column<Int> = this.usint32(Efn.KILLS)

  /**
   * @since 0.1.0
   */
  val assists: Column<Int> = this.usint32(Efn.ASSISTS)

  /**
   * @since 0.1.0
   */
  val deaths: Column<Int> = this.usint32(Efn.DEATHS)

  /**
   * @since 0.1.0
   */
  val destroyedMonuments: Column<Int> = this.usint32(Efn.DESTROYED_MONUMENTS)

  /**
   * @since 0.1.0
   */
  val playedGames: Column<Int> = this.usint32(Efn.PLAYED_GAMES)

  /**
   * @since 0.1.0
   */
  val gamesWon: Column<Int> = this.usint32(Efn.GAMES_WON)

  /**
   * @since 0.1.0
   */
  val gamesLost: Column<Int> = this.usint32(Efn.GAMES_LOST)

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}