package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.team.Team

interface TeamRepository {

  /**
   * @since 0.1.0
   */
  suspend fun findTeams(): List<Team>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findTeamByIdentifier(
    identifier: UUID
  ): Team?

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  suspend fun findTeamByName(
    name: String,
    ignoreCase: Boolean
  ): Team?

  /**
   * @param team
   *
   * @since 0.1.0
   */
  suspend fun insertTeam(
    team: Team
  ): Int

  /**
   * @param team
   *
   * @since 0.1.0
   */
  suspend fun updateTeam(
    team: Team
  ): Int

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteTeamByIdentifier(
    identifier: UUID
  ): Int
}