package me.kvdpxne.dtm.data.tables

import java.util.UUID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object GameTeamsTable : Table("game_teams") {

  val gameIdentifier: Column<UUID> = this.uuid("game_identifier")
    .references(GameTable.identifier)

  val teamIdentifier: Column<UUID> = this.uuid("team_identifier")
    .references(TeamTable.identifier)
}