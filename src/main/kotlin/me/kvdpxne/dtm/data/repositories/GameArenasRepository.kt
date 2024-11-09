package me.kvdpxne.dtm.data.repositories

import kotlinx.coroutines.flow.Flow
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.shared.GameUuid
import me.kvdpxne.dtm.team.Team

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
    identifier: GameUuid
  ): Flow<Arena>

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

  /**
   * @param game
   *
   * @since 0.1.0
   */
  suspend fun deleteGameArenas(
    game: Game<Team>
  )
}