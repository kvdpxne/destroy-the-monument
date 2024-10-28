package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team

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
  ): List<Team>

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
}