package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game

/**
 * @since 0.1.0
 */
interface GameArenasRepository {

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findGameArenasByGameIdentifier(
    identifier: UUID
  ): List<Arena>

  suspend fun insertGameArena(game: Game<*>, gameArena: Arena)
}