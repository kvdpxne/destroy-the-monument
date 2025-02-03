package me.kvdpxne.dtm.data.tables.relationships

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import me.kvdpxne.dtm.data.tables.TableArena
import me.kvdpxne.dtm.data.tables.TableGame
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TableGameTeams : Table(En.GAME_TEAMS) {

  /**
   * @since 0.1.0
   */
  val gameIdentifier: Column<UUID> = this.uuid(Efn.GAME_IDENTIFIER)
    .references(TableGame.identifier)

  /**
   * @since 0.1.0
   */
  val teamIdentifier: Column<UUID> = this.uuid(Efn.TEAM_IDENTIFIER)
    .references(TableArena.identifier)
}