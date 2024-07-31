package me.kvdpxne.dtm.game

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
private val logger: KLogger = KotlinLogging.logger { }

/**
 * @since 0.1.0
 */
object GameManager {

  /**
   * @since 0.1.0
   */
  private val _games: MutableMap<String, Game> = mutableMapOf()

  init {
    // TODO Delete in the future.
    // Information about games should be loaded into memory only when it is
    // really needed and removed when it is no longer needed.
    for (game: Game in GameService.findGames()) {
      this._games[game.identifier] = game
    }
  }

  /**
   * @since 0.1.0
   */
  val games: List<Game>
    get() = this._games.values.toList()

  /**
   * @since 0.1.0
   */
  val size: Int
    get() = this._games.size

  /**
   * Tries to find a [Game] by [Game.identifier].
   */
  fun findGameByIdentifier(
    identifier: String
  ): Game? {
    return this._games[identifier]
  }

  /**
   * Tries to find a [Game] by [Game.name].
   */
  fun findGameByName(
    name: String
  ): Game? {
    return this._games.values.find {
      it.name.equals(name, true)
    }
  }

  /**
   *
   */
  fun findByUser(user: User): Game? {
    return this._games.values.find {
      it.isInGame(user)
    }
  }
}