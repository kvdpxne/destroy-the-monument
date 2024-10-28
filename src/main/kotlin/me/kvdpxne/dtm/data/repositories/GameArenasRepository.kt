package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.game.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.Team

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

  /**
   * @param game
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun insertGameArena(
    game: Game<Team>,
    arena: Arena
  )

  /**
   * @param game
   * @param arena
   *
   * @since 0.1.0
   */
  suspend fun deleteGameArena(
    game: Game<Team>,
    arena: Arena
  )
}