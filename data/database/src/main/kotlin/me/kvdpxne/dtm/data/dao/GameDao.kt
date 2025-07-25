package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawGame
import me.kvdpxne.dtm.data.repositories.GameRepository

object GameDao : GameRepository {

  override suspend fun readGames(): Collection<RawGame> {
    TODO("Not yet implemented")
  }

  override suspend fun readGameIdentifiers(): Collection<UUID> {
    TODO("Not yet implemented")
  }

  override suspend fun readGameNames(): Collection<String> {
    TODO("Not yet implemented")
  }

  override suspend fun findGameByIdentifierOrNull(identifier: UUID): RawGame? {
    TODO("Not yet implemented")
  }

  override suspend fun findGameByNameOrNull(name: String): RawGame? {
    TODO("Not yet implemented")
  }

  override suspend fun insertGame(game: RawGame?): Int {
    TODO("Not yet implemented")
  }

  override suspend fun updateGame(game: RawGame?): Int {
    TODO("Not yet implemented")
  }

  override suspend fun deleteGameByIdentifier(identifier: UUID): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun countGames(): Long {
    TODO("Not yet implemented")
  }
}