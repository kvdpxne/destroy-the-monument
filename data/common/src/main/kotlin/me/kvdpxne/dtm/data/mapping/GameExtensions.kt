package me.kvdpxne.dtm.data.mapping

import me.kvdpxne.dtm.data.raw.RawGame
import me.kvdpxne.dtm.data.shared.mapNotNullTo
import me.kvdpxne.dtm.game.Game

/**
 * @since 0.1.0
 */
fun Game.toRawGame(): RawGame {
  return RawGame(
    this.identifier,
    this.teams.toRawTeams(),
    this.arenas.toRawArenas(),
    this.name
  )
}

/**
 * @param initialCapacity
 * @param distinct
 *
 * @since 0.1.0
 */
fun Iterable<Game?>.toRawGames(
  initialCapacity: Int = 12,
  distinct: Boolean = true
): List<RawGame> {
  return mapNotNullTo(
    this,
    initialCapacity,
    distinct,
    Game::toRawGame
  )
}