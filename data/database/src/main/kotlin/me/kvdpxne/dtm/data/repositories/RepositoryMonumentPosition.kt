package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.data.raw.RawMonumentPosition

interface RepositoryMonumentPosition {

  suspend fun readMonumentPositions(): Collection<RawMonumentPosition>

  suspend fun readMonumentPositionIdentifiers(): Collection<String>

  suspend fun findMonumentPositionByIdentifierOrNull(
    identifier: String
  ): RawMonumentPosition?

  suspend fun insertMonumentPosition(
    monumentPosition: RawMonumentPosition
  ): Int

  suspend fun updateMonumentPosition(
    monumentPosition: RawMonumentPosition
  ): Int

  suspend fun deleteMonumentPositionByIdentifier(
    identifier: String
  ): Boolean

  suspend fun countMonumentPositions(): Long
}