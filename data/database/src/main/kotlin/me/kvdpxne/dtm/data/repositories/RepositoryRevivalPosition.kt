package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.data.raw.RawRevivalPosition

interface RepositoryRevivalPosition {

  suspend fun readRevivalPositions(): Collection<RawRevivalPosition>

  suspend fun readRevivalPositionIdentifiers(): Collection<String>

  suspend fun findRevivalPositionByIdentifierOrNull(
    identifier: String
  ): RawRevivalPosition?

  suspend fun insertRevivalPosition(revivalPosition: RawRevivalPosition): Int

  suspend fun updateRevivalPosition(revivalPosition: RawRevivalPosition): Int

  suspend fun deleteRevivalPositionByIdentifier(identifier: String): Boolean

  suspend fun countRevivalPositions(): Long
}