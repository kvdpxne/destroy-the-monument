package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawTeam

/**
 * @since 0.1.0
 */
interface RepositoryTeam {

  /**
   * @since 0.1.0
   */
  suspend fun readTeams(): Collection<RawTeam>

  /**
   * @since 0.1.0
   */
  suspend fun readTeamIdentifiers(): Collection<UUID>

  /**
   * @since 0.1.0
   */
  suspend fun findTeamByIdentifierOrNull(
    identifier: UUID
  ): RawTeam?

  /**
   * @since 0.1.0
   */
  suspend fun findTeamByNameOrNull(
    name: String
  ): RawTeam?

  /**
   * @since 0.1.0
   */
  suspend fun containsTeamByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun containsTeamByName(
    name: String
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun insertTeam(
    team: RawTeam?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateTeam(
    team: RawTeam?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteTeamByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun truncateTeams(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countTeams(): Long
}