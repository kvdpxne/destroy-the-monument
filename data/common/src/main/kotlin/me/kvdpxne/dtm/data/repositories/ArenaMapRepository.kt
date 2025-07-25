package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface ArenaMapRepository {

  suspend fun readArenaMaps(): Collection<Pair<RawArenaMap, ValidationResult>>

  suspend fun readArenaMapIdentifiers(): Collection<UUID>

  suspend fun readArenaMapNames(): Collection<String>

  suspend fun findArenaMapByIdentifierOrNull(identifier: UUID): Pair<RawArenaMap, ValidationResult>?

  suspend fun findArenaMapByNameOrNull(name: String): Pair<RawArenaMap, ValidationResult>?

  suspend fun containsArenaMapByIdentifier(identifier: UUID): Boolean

  suspend fun containsArenaMapByName(name: String): Boolean

  suspend fun insertArenaMap(arenaMap: RawArenaMap?): Pair<RawArenaMap?, ValidationResult>

  suspend fun updateArenaMap(arenaMap: RawArenaMap?): Pair<RawArenaMap?, ValidationResult>

  suspend fun deleteArenaMapByIdentifier(identifier: UUID): Int

  suspend fun deleteArenaMap(arenaMap: RawArenaMap): Int {
    return this.deleteArenaMapByIdentifier(arenaMap.identifier)
  }

  suspend fun truncateArenaMaps(): Int

  suspend fun countArenaMaps(): Long
}