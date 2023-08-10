package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object GameTeamsTable : Table<Nothing>("game_teams") {

  val game = varchar("game")
  val team = varchar("team")
}

object GameTeamsDao {

  fun findAllByGameIdentifier(identifier: UUID): Collection<Team> {
    return database.from(GameTeamsTable)
      .select()
      .where {
        GameTeamsTable.game eq identifier.toString()
      }
      .mapNotNull {
        val team = TeamDao.findByIdentifier(
          UUID.fromString(
            it[GameTeamsTable.team]
          )
        )

        Team(team!!)
      }
  }

  fun insert(game: Game, team: Team) {
    database.insert(GameTeamsTable) {
      set(GameTeamsTable.game, game.identifier.toString())
      set(GameTeamsTable.team, DefaultTeamColor.findByIdentity(team.identity)?.identifier.toString())
    }
  }
}