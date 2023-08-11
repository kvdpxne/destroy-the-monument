package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object GameTeamsTable : Table<Nothing>("game_teams") {

  val game = varchar("game")
  val team = varchar("team")
}

object GameTeamsDao {

  fun findAllByGameIdentifier(identifier: UUID) = database.from(GameTeamsTable)
    .select()
    .where { GameTeamsTable.game eq identifier.toString() }
    .mapNotNull {
      val team = TeamDao.findByIdentifier(
        UUID.fromString(
          it[GameTeamsTable.team]
        )
      )
      Team(team!!)
    }

  fun insert(game: Game, team: Team) = database.insert(GameTeamsTable) {
    set(it.game, game.identifier.toString())
    set(it.team, DefaultTeamColor.findByIdentity(team.identity)?.identifier.toString())
  }
}