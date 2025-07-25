package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface TeamRepository {

  /**
   * @since 0.1.0
   */
  suspend fun readTeams(): Collection<Pair<RawTeam, ValidationResult>>

  /**
   * @since 0.1.0
   */
  suspend fun readTeamIdentifiers(): Collection<UUID>

  /**
   * @since 0.1.0
   */
  suspend fun readTeamNames(): Collection<String>

  /**
   * @since 0.1.0
   */
  suspend fun findTeamByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawTeam, ValidationResult>?

  /**
   * @since 0.1.0
   */
  suspend fun findTeamByNameOrNull(
    name: String
  ): Pair<RawTeam, ValidationResult>?

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
  ): Pair<RawTeam?, ValidationResult>

  /**
   * @since 0.1.0
   */
  suspend fun updateTeam(
    team: RawTeam?
  ): Pair<RawTeam?, ValidationResult>

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