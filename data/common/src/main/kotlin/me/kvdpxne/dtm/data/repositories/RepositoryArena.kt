package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawArena

interface RepositoryArena {

  suspend fun readArenas(): Collection<RawArena>

  suspend fun readArenaIdentifiers(): Collection<String>

  suspend fun findArenaByIdentifierOrNull(identifier: UUID): RawArena?

  suspend fun findArenaByNameOrNull(name: String): RawArena?

  suspend fun insertArena(arena: RawArena)

  suspend fun updateArena(arena: RawArena)

  suspend fun deleteArenaByIdentifier(identifier: UUID): Boolean

  suspend fun countArenas(): Long
}