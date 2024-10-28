package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.team.Team

/**
 * @since 0.1.0
 */
interface GameTeamsRepository {

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findGameTeamsByGameIdentifier(
    identifier: UUID
  ): Flow<Team>

  /**
   * @param game
   * @param team
   *
   * @since 0.1.0
   */
  suspend fun insertGameTeam(
    game: Game<Team>,
    team: Team
  )

  /**
   * @param game
   * @param team
   *
   * @since 0.1.0
   */
  suspend fun deleteGameTeam(
    game: Game<Team>,
    team: Team
  )

  /**
   * @param game
   *
   * @since 0.1.0
   */
  suspend fun deleteGameTeams(
    game: Game<Team>
  )
}