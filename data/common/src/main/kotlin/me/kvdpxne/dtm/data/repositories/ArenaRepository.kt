package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface ArenaRepository {

  /**
   * @since 0.1.0
   */
  suspend fun readArenaIdentifiers(): Collection<UUID>

  /**
   * @since 0.1.0
   */
  suspend fun readArenaNames(): Collection<String>

  /**
   * @since 0.1.0
   */
  suspend fun findArenaMonumentPositionsByIdentifier(
    identifier: UUID
  ): Collection<Pair<RawMonumentPosition, ValidationResult>>

  /**
   * @since 0.1.0
   */
  suspend fun findArenaRevivalPositionsByIdentifier(
    identifier: UUID
  ): Collection<Pair<RawRevivalPosition, ValidationResult>>

  /**
   * @since 0.1.0
   */
  suspend fun findArenaByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawArena, ValidationResult>?

  /**
   * @since 0.1.0
   */
  suspend fun findArenaByNameOrNull(
    name: String
  ): Pair<RawArena, ValidationResult>?

  /**
   * @since 0.1.0
   */
  suspend fun containsArenaByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun containsArenaByName(
    name: String
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun insertArena(
    arena: RawArena?
  ): Pair<RawArena?, ValidationResult>

  /**
   * @param identifier
   * @param monumentPosition
   *
   * @since 0.1.0
   */
  suspend fun insertArenaMonumentPosition(
    identifier: UUID?,
    monumentPosition: RawMonumentPosition?
  ): Pair<Int?, ValidationResult>

  /**
   * @param identifier
   * @param revivalPosition
   *
   * @since 0.1.0
   */
  suspend fun insertArenaRevivalPosition(
    identifier: UUID?,
    revivalPosition: RawRevivalPosition?
  ): Pair<Int?, ValidationResult>

  /**
   * @since 0.1.0
   */
  suspend fun updateArena(
    arena: RawArena?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteArenaByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteArena(
    arena: RawArena
  ): Int {
    return this.deleteArenaByIdentifier(arena.identifier)
  }

  /**
   * @since 0.1.0
   */
  suspend fun deleteArenaMonumentPosition(
    identifier: UUID,
    monumentPositionIdentifier: UUID,
    deleteReference: Boolean = false
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteArenaMonumentPositions(
    identifier: UUID,
    deleteReference: Boolean = false
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteArenaRevivalPosition(
    identifier: UUID,
    revivalPositionIdentifier: UUID,
    deleteReference: Boolean = false
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteArenaRevivalPositions(
    identifier: UUID,
    deleteReference: Boolean = false
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun truncateArenas(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countArenas(): Long

  /**
   * @since 0.1.0
   */
  suspend fun countArenaMonumentPositionsByIdentifier(
    identifier: UUID,
    teamIdentifier: UUID? = null
  ): Long

  /**
   * @since 0.1.0
   */
  suspend fun countArenaRevivalPositionsByIdentifier(
    identifier: UUID,
    teamIdentifier: UUID? = null
  ): Long
}