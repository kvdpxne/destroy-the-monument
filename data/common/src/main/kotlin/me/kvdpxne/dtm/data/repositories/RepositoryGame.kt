package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawGame

interface RepositoryGame {

  suspend fun readGames(): Collection<RawGame>

  suspend fun readGameIdentifiers(): Collection<String>

  suspend fun findGameByIdentifierOrNull(identifier: UUID): RawGame?

  suspend fun findGameByNameOrNull(name: String): RawGame?

  suspend fun insertGame(game: RawGame)

  suspend fun updateGame(game: RawGame)

  suspend fun deleteGameByIdentifier(identifier: UUID): Boolean

  suspend fun countGames(): Long
}